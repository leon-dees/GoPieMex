package mx.dees.gopiemex.activities;

import android.app.Activity;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.dataservice.AccountDataTask;
import mx.dees.gopiemex.dataservice.DeviceDataTask;
import mx.dees.gopiemex.models.GoodsListGoPie;
import mx.dees.gopiemex.payment.PaymentTokenResponse;
import mx.dees.gopiemex.resources.TravelCallApi;
import mx.dees.gopiemex.resources.TravelTask;
import mx.dees.gopiemex.resources.TravelUtility;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderResponse;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginRequest;

public class ActivateActivity extends Activity {
    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION).clientId("AZmvwaMevSXtZhHXhe56CYVAbdJE8yWjyforc70nUbVka387RZevALPLys46ruWKdUFLX0CiiyXmbkno");

    private TextView lblToken = null;
    private TextView lblPaypal = null;

    private int iPos = 0;

    private Button btnGood1 = null;
    private Button btnGood2 = null;

    private Spinner spiGoods = null;
    private ArrayList<String> arrListGoods = null;
    private Gson gson = new Gson();
    private String payGood = null;
    private String payPrice = null;
    private String payCode = null;
    private TextView lblGoodsList = null;

    TextView lblGoodsListData = null;
    TextView lblGoodsListPrice = null;
    TextView lblGoodsListCode = null;

    TextView lblUserCode = null;
    TextView lblUserId = null;
    TextView lblStreamNo = null;

    private TextView lblRe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        this.lblToken = findViewById(R.id.lblToken);
        this.spiGoods = findViewById(R.id.spinGoods);
        this.lblGoodsList = findViewById(R.id.lblGoodsList);
        this.lblGoodsListData = findViewById(R.id.lblGoodsListName);
        this.lblGoodsListPrice = findViewById(R.id.lblGoodsListPrice);
        this.lblGoodsListCode = findViewById(R.id.lblGoodsListCode);
        this.lblUserCode = findViewById(R.id.lblUserCode);
        this.lblUserId = findViewById(R.id.lblUserId);
        this.lblStreamNo = findViewById(R.id.lblStreamNo);
        this.lblRe = findViewById(R.id.lblRe);
        this.btnGood1 = findViewById(R.id.btnGood1);
        this.btnGood2 = findViewById(R.id.btnGood2);


        this.spiGoods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<GoodsListGoPie> goods = new ArrayList<>();
                goods = gson.fromJson(lblGoodsList.getText().toString(),new TypeToken<List<GoodsListGoPie>>(){}.getType());
                TextView lblGoodsListData = findViewById(R.id.lblGoodsListName);
                TextView lblGoodsListPrice = findViewById(R.id.lblGoodsListPrice);
                TextView lblGoodsListCode = findViewById(R.id.lblGoodsListCode);
                TextView lblUserCode = findViewById(R.id.lblUserCode);
                TextView lblUserId = findViewById(R.id.lblUserId);
                TextView lblStreamNo = findViewById(R.id.lblStreamNo);
                TextView lblToken = findViewById(R.id.lblToken);
                lblGoodsListData.setText(goods.get(i).L1);
                //lblGoodsListPrice.setText("0.01");
                lblGoodsListPrice.setText(goods.get(i).L3);
                lblGoodsListCode.setText(goods.get(i).L2);
                lblUserCode.setText(goods.get(i).L5);
                lblUserId.setText(goods.get(i).L4);
                lblStreamNo.setText(goods.get(i).L6);
                lblToken.setText(goods.get(i).L7);

                String strGood = spiGoods.getItemAtPosition(spiGoods.getSelectedItemPosition()).toString();
                System.out.println("GoPie: ActivateActivity onCreate setOnItemSelectedListener: "+strGood);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();
        this.lblRe.setText(intent.getStringExtra("arg1"));
        TravelUtility obj2 = new TravelUtility();
        DeviceDataTask task = new DeviceDataTask(this);
        task.execute(new String[]{obj2.getImei()});



        Intent intent2 = new Intent(this, PayPalService.class);
        intent2.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void btnPaymentToken(View v)
    {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(this.lblGoodsListPrice.getText().toString()), "MXN", this.lblGoodsListData.getText().toString(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }


    public  void btnGood1(View v)
    {
        this.iPos = 1;
        ArrayList<GoodsListGoPie> goods = new ArrayList<>();
        goods = gson.fromJson(lblGoodsList.getText().toString(),new TypeToken<List<GoodsListGoPie>>(){}.getType());
        PayPalPayment payment = new PayPalPayment(new BigDecimal(goods.get(this.iPos).L3), "USD", goods.get(this.iPos).L1, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }

    public  void btnGood2(View v)
    {
        this.iPos = 2;
        ArrayList<GoodsListGoPie> goods = new ArrayList<>();
        goods = gson.fromJson(lblGoodsList.getText().toString(),new TypeToken<List<GoodsListGoPie>>(){}.getType());
        PayPalPayment payment = new PayPalPayment(new BigDecimal(goods.get(this.iPos).L3), "USD", goods.get(this.iPos).L1, PayPalPayment.PAYMENT_INTENT_SALE);
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(this.lblGoodsListPrice.getText().toString()), "MXN", this.lblGoodsListData.getText().toString(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }
    public void btnDeviceData(View v)
    {
        //String imei = sp.getString("imei", "");
        //SharedPreferences sp = getApplicationContext().getSharedPreferences("gopie-pref", 0);
        //String hs = sp.getString("imei", "N/A");
        //TravelUtility obj2 = new TravelUtility();
        //DeviceDataTask task = new DeviceDataTask(this);
        //task.execute(new String[]{obj2.getImei()});
        //task.execute(new String[]{(this.lblIMEI.getText().toString().length() > 0) ? this.lblIMEI.getText().toString() : "empty"});

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if (confirm != null) {
                try {
                    PaymentTokenResponse obj = new PaymentTokenResponse();
                    obj = gson.fromJson(gson.toJson(confirm), PaymentTokenResponse.class);
                    ArrayList<GoodsListGoPie> goods = new ArrayList<>();
                    goods = gson.fromJson(lblGoodsList.getText().toString(),new TypeToken<List<GoodsListGoPie>>(){}.getType());
                    TravelAPI(7, new String[]{goods.get(iPos).L1,goods.get(iPos).L4, goods.get(iPos).L2, goods.get(iPos).L5, goods.get(iPos).L7, gson.toJson(confirm), goods.get(iPos).L3});
                    System.out.println("GoPie: ActivateActivity onActivityResult token : "+ gson.toJson(confirm)/*+ confirm.toJSONObject().toString(4)*/);
                    //this.lblPaypal.setText("status: "+obj.d.b + ", id: " + obj.d.c + ", token: " + obj.d.f +", userId: " + lblUserId.getText().toString() + ", userCode" + lblUserCode.getText().toString() + ", goodsId: " +lblGoodsListCode.getText().toString());
                } catch (Exception e) {
                    System.out.println("GoPie: ActivateActivity onActivityResult Exception error : "+ e.toString());
                    //Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("GoPie: ActivateActivity onActivityResult cancel");
            Intent intent = new Intent(this,EndActivity.class);
            intent.putExtra("arg1", resultCode);
            intent.putExtra("arg2", 2);
            this.startActivity(intent);

            //Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            System.out.println("GoPie: ActivateActivity onActivityResult invalid extras");
            Intent intent = new Intent(this,EndActivity.class);
            intent.putExtra("arg1", resultCode);
            intent.putExtra("arg2", 2);
            this.startActivity(intent);
        }
    }

    private void TravelAPI(int iC, String[] arr)
    {
        TravelTask task = new TravelTask(this, iC);
        task.execute(arr);
    }


}
