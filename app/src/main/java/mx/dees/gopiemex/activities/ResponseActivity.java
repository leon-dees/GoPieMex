package mx.dees.gopiemex.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.resources.TravelTask;
import mx.dees.gopiemex.resources.TravelUtility;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginRequest;

public class ResponseActivity extends Activity {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //private Gson gson = new Gson();
    private TextView lblRe = null;
    private TextView lblLogin = null;
    private TextView lblGood = null;
    private TextView lblUser = null;
    private TextView lblImei = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        TravelUtility obj2 = new TravelUtility();
        this.lblRe = findViewById(R.id.lblRe);
        this.lblLogin = findViewById(R.id.lblLogin);
        this.lblGood = findViewById(R.id.lblGood);
        this.lblUser = findViewById(R.id.lblUser);
        this.lblImei = findViewById(R.id.lblImei);
        Intent intent = getIntent();
        TravelGrpUserLoginRequest obj = this.gson.fromJson(intent.getStringExtra("arg1"), TravelGrpUserLoginRequest.class);
        this.lblRe.setText(this.gson.toJson(obj));
        this.lblImei.setText(obj2.getImei());
    }

    public void btnUser(View v)
    {
        this.TravelAPI(3, this.lblLogin.getText().toString());
    }
    public void btnGood(View v) { this.TravelAPI(2, this.lblLogin.getText().toString()); }
    public void btnLogin(View v) { this.TravelAPI(1, this.lblRe.getText().toString()); }
    public void btnLogout(View v) { this.TravelAPI(4, this.lblLogin.getText().toString()); }
    public void btnCreateOrder(View v) { this.TravelAPI(5, this.lblLogin.getText().toString()); }

    private void TravelAPI(int iC, String json)
    {
        TravelTask task = new TravelTask(this, iC);
        String[] arr = new String[]{json, this.lblGood.getText().toString(), this.lblUser.getText().toString()};
        task.execute(arr);
    }
}
