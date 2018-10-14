package mx.dees.gopiemex.dataservice;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.resources.TravelCallApi;
import mx.dees.gopiemex.resources.TravelUtility;

/**
 * Created by leon on 22/03/18.
 */

public class AccountDataTask extends AsyncTask<String, Integer, String> {

    private Activity act = null;
    private ProgressDialog dialog = null;
    private TextView lblUserGoPie = null;
    //private TextView lblRe = null;
    //private TextView lblHash = null;

    public AccountDataTask(Activity act){
        this.act = act;
        this.dialog = new ProgressDialog(act);
        this.lblUserGoPie = this.act.findViewById(R.id.lblUserGoPie);
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
            return obj.getAccountData(strings[0]);
        }
        catch (IOException e)
        {
            return "Error AccountDataTask doInBack: " + e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {

            this.lblUserGoPie.setText(result);
        }catch (Exception e)
        {
            //this.lblHash.setText(e.toString());

        }
        finally {
            if(this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }
        }
    }
}
