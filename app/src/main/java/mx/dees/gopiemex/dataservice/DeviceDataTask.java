package mx.dees.gopiemex.dataservice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.resources.TravelCallApi;
import mx.dees.gopiemex.resources.TravelTask;
import mx.dees.gopiemex.resources.TravelUtility;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginRequest;

/**
 * Created by leon on 22/03/18.
 */

public class DeviceDataTask extends AsyncTask<String, Integer, String> {

    private Activity act = null;
    private ProgressDialog dialog = null;
    private TextView lblRe = null;

    public DeviceDataTask(Activity act){
        this.act = act;
        this.lblRe = this.act.findViewById(R.id.lblRe);
        this.dialog = new ProgressDialog(act);

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.dialog.setMessage(this.act.getString(R.string.dialogMsg));
        this.dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        TravelCallApi obj = new TravelCallApi();
        try {
            System.out.println("GoPie: DeviceDataTask doInBack imei: " + strings[0]);
            return obj.setDeviceData(strings[0]);
        }
        catch (IOException e)
        {
            return "Error DeviceDataTask doInBack: " + e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            TravelUtility obj2 = new TravelUtility();
            TravelAPI(6, new String[]{this.lblRe.getText().toString(), obj2.getImei()/*this.lblIMEI.getText().toString()*/});

        }catch (Exception e)
        {
            this.lblRe.setText(e.toString());
        }
        finally {
            if(this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }
        }
    }

    private void TravelAPI(int iC, String[] arr)
    {
        TravelTask task = new TravelTask(this.act, iC);
        task.execute(arr);
    }

}
