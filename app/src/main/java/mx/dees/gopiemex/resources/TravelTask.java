package mx.dees.gopiemex.resources;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.activities.ActivateActivity;
import mx.dees.gopiemex.activities.EndActivity;
import mx.dees.gopiemex.models.GoodsListGoPie;
import mx.dees.gopiemex.models.TravelDataList;
import mx.dees.gopiemex.models.TravelInit;
import mx.dees.gopiemex.models.TravelToken;
import mx.dees.gopiemex.models.TravelWrapper;
import mx.dees.gopiemex.payment.PaymentTokenResponse;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderResponse;

/**
 * Created by leon on 7/03/18.
 */

public class TravelTask extends AsyncTask<String, Integer, String>{

    private Activity act = null;
    private int iC = 0;
    private ProgressDialog dialog = null;
    private TextView lblLogin = null;
    private TextView lblGood = null;
    private TextView lblUser = null;
    private TextView lblGoods = null;
    private TextView lblGoodsResponse = null;
    private TextView lblUsers = null;
    private TextView lblUsersResponse = null;
    private TextView lblLogout = null;
    private TextView lblCreateOrder = null;
    private TextView lblToken = null;
    private TextView lblTokenResponse = null;

    private TextView lblGoodsList = null;
    //private Spinner spiLang = null;


    private Spinner spiGoods = null;
    //private TextView lblPaypal = null;



    public TravelTask(Activity act, int iC)
    {
        this.iC = iC;
        this.act = act;
        this.dialog = new ProgressDialog(act);
        this.lblLogin = this.act.findViewById(R.id.lblLogin);
        this.lblGood = this.act.findViewById(R.id.lblGood);
        this.lblUser = this.act.findViewById(R.id.lblUser);
        this.lblLogout = this.act.findViewById(R.id.lblLogout);
        this.lblCreateOrder = this.act.findViewById(R.id.lblCreateOrder);
        this.lblGoodsList = this.act.findViewById(R.id.lblGoodsList);
        this.spiGoods = this.act.findViewById(R.id.spinGoods);
        //this.lblPaypal = this.act.findViewById(R.id.lblPaypal);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setMessage(this.act.getString(R.string.dialogMsg));
        this.dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String strRe = "";
        TravelInit obj = new TravelInit();
        TravelCallApi api = new TravelCallApi();
        Gson gson = new Gson();
        try {
            switch (iC) {
                case 1:
                    strRe = api.GrpUserLogin(strings[0]);
                    Log.v("LogGopie","TravelTask doinbackground Login strRe: " + strRe);
                    break;
                case 2:
                    strRe = api.QueryGrpOfferList(strings[0]);
                    break;
                case 3:
                    strRe = api.QuerySubUserListInfo(strings[0]);
                    break;
                case 4:
                    strRe = api.GrpUserLogout(strings[0]);
                    break;
                case 5:
                    strRe = api.GrpCreateOrder(strings);
                    break;
                case 6:
                    obj.tokenResponse = api.GrpUserLogin(strings[0]);
                    TravelToken obj3 = gson.fromJson(obj.tokenResponse, TravelToken.class);
                    strRe = api.setGrpUserLoginGoPie(obj3.data.accessToken,strings[1],obj3.data.userId, obj3.streamNo);

                    obj.goodsResponse = gson.toJson(api.QueryGrpOfferList(obj.tokenResponse, 1));

                    obj.usersResponse = gson.toJson(api.QuerySubUserListInfo(obj.tokenResponse, 1));
                    strRe = gson.toJson(obj);
                    TravelWrapper objW = gson.fromJson(obj.usersResponse, TravelWrapper.class);

                    for(int i=0; objW.data.dataList.length > i; i++)
                    {
                        Log.v("LogGopie","TravelTask doinbackground case 5 userCode: "+objW.data.dataList[i].userCode + ", imei: "+objW.data.dataList[i].imei);
                        if(objW.data.dataList[i].imei.equals(strings[1]))
                        {
                            api.setSubUserListInfoGoPie(strings[1], obj.usersResponse, objW.data.dataList[i].customerId, objW.data.dataList[i].userCode,obj3.data.accessToken);
                        }
                        else if(objW.data.dataList[i].imei.equals("864652031538617"))
                        {
                            api.setSubUserListInfoGoPie(strings[1], obj.usersResponse, objW.data.dataList[i].customerId, objW.data.dataList[i].userCode,obj3.data.accessToken);
                        }
                    }



                    TravelDataList obj2 = new TravelDataList();
                    obj2 = gson.fromJson(obj.goodsResponse, TravelDataList.class);
                    for(int i=0; i < obj2.dataList.length; i++)
                    {

                        api.setQueryGrpOfferListGoPie(gson.toJson(obj2.dataList[i]), obj2.dataList[i].goodsName,obj2.dataList[i].goodsId, obj2.dataList[i].attrMap.timeZone, obj2.dataList[i].goodsPrice, obj2.dataList[i].goodsCode,strings[1], gson.fromJson(obj.tokenResponse, TravelToken.class).data.accessToken);
                    }
                    strRe =api.getQueryGrpOfferListGoPie(gson.fromJson(obj.tokenResponse, TravelToken.class).data.accessToken);
                    break;
                case 7:
                    String str = TravelUtility.submitCreateOrder(strings[0], strings[1],strings[2], strings[3]);
                    strRe = api.submitGrpCreateOrder(str,strings[4]);
                    TravelGrpCreateOrderResponse obj5 = gson.fromJson(strRe, TravelGrpCreateOrderResponse.class);

                    PaymentTokenResponse obj7 = new PaymentTokenResponse();
                    obj7 = gson.fromJson(strings[5], PaymentTokenResponse.class);
                    api.setGrpCreateOrderGoPie(strings[3], strRe,obj5.data.orderSN,obj5.data.orderId, strings[4], strings[2], obj7.d.c, obj7.d.f );

                    strRe = strings[5] + "@"+strRe + "@"+strings[3] + "@"+strings[6];
                    //TravelCallApi api = new TravelCallApi();
                    //TravelGrpCreateOrderResponse obj5 = gson.fromJson(api.submitGrpCreateOrder(str,lblToken.getText().toString()), TravelGrpCreateOrderResponse.class);
                    break;
                case 8:
                    //showChangeLangDialog(Integer.parseInt(strings[0]));
                    //strRe = showChangeLangDialog(Integer.parseInt(strings[0]));
                    break;
                default:
                    break;
            }
            return strRe;
        }
        catch (IOException e)
        {
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        try{
            Gson gson = new Gson();
            switch (iC) {
                case 1:
                    this.lblLogin.setText(result);
                    break;
                case 2:
                    this.lblGood.setText(result);
                    break;
                case 3:
                    this.lblUser.setText(result);
                    break;
                case 4:
                    this.lblLogout.setText(result);
                    break;
                case 5:
                    this.lblCreateOrder.setText(result);
                    break;
                case 6:
                    this.lblGoodsList.setText(LoadSpinnerData(result, this.act));
                    break;
                case 7:
                    Intent intent = new Intent(this.act,EndActivity.class);
                    intent.putExtra("arg1", result);
                    intent.putExtra("arg2", 1);
                    this.act.startActivity(intent);
                    System.out.println("GoPie: TravelTask onPostExecute orderCreate: "+result);
                    break;
                case 8:
                    //setLangRecreate(result);
                    //System.out.println("GoPie: TravelTask onPostExecute select Language: "+result);
                    break;
                default:
                    break;
            }


        }
        catch (Exception e)
        {}
        finally {
            if(this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }
        }
    }

    private String LoadSpinnerData(String json, Activity act)
    {
        Button btnGood1 = null;
        Button btnGood2 = null;
        btnGood1 = act.findViewById(R.id.btnGood1);
        btnGood2 = act.findViewById(R.id.btnGood2);
        ArrayList<String> arrListGoods = new ArrayList<>();
        ArrayList<GoodsListGoPie> goods = new ArrayList<GoodsListGoPie>();

        Gson gson = new Gson();
        GoodsListGoPie[] obj = gson.fromJson(json, GoodsListGoPie[].class);
        for(int i=0; i<obj.length;i++)
        {
            goods.add(obj[i]);
            arrListGoods.add(obj[i].L1);
        }
        btnGood1.setText(obj[1].L8);
        btnGood2.setText(obj[2].L8);
        this.spiGoods.setAdapter(new ArrayAdapter<String>(this.act, android.R.layout.simple_spinner_dropdown_item, arrListGoods));
        return gson.toJson(goods);
    }

   // public String showChangeLangDialog( int langpos) {
        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.act);
        LayoutInflater inflater = this.act.getLayoutInflater();
        View dialogView = inflater.inflate( R.layout.language_dialog_obs, null);
        dialogBuilder.setView(dialogView);*/

        //final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);

        /*dialogBuilder.setTitle(this.act.getResources().getString(R.string.lang_dialog_title));
        dialogBuilder.setMessage(this.act.getResources().getString(R.string.lang_dialog_message));
        dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {*/
               // int langpos = spinner1.getSelectedItemPosition();
               /* switch(langpos) {
                    case 0: //English
                        //PreferenceManager.getDefaultSharedPreferences(act.getApplicationContext()).edit().putString("LANG", "en").commit();
                        //setLangRecreate("en");

                        return "en";
                    case 1: //Español
                        //PreferenceManager.getDefaultSharedPreferences(this.act.getApplicationContext()).edit().putString("LANG", "es").commit();
                        //setLangRecreate("es");
                        return "es";
                    default: //By default set to english
                        //PreferenceManager.getDefaultSharedPreferences(this.act.getApplicationContext()).edit().putString("LANG", "en").commit();
                        //setLangRecreate("en");
                        return "en";
                }*/
        /*    }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();*/
   // }

   /* public void setLangRecreate(String langval) {

        PreferenceManager.getDefaultSharedPreferences(this.act.getApplicationContext()).edit().putString("LANG", langval).commit();
        Configuration config = this.act.getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);

        Locale.setDefault(locale);
        config.locale = locale;
        this.act.getBaseContext().getResources().updateConfiguration(config, this.act.getBaseContext().getResources().getDisplayMetrics());
        this.act.recreate();
        System.out.println("GoPie: TravelTask setLangRecreate: " + langval);

    }
*/
}
