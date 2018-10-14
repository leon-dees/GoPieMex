package mx.dees.gopiemex.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.payment.PaymentTokenResponse;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderResponse;

public class EndActivity extends Activity {

    private TextView lblPaypal = null;
    private TextView lblUcloudlink = null;
    private String strArg1 = "";
    private int iC =0;
    private String[] arrArg1 = new String[] {};
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        this.lblPaypal = findViewById(R.id.lblPaypal);
        this.lblUcloudlink = findViewById(R.id.lblUcloudlink);
        Intent intent = getIntent();
        this.strArg1 = intent.getStringExtra("arg1");
        this.iC = intent.getIntExtra("arg2",0);
        System.out.println("GoPie: onCreate intent: " + this.strArg1 );

        switch (this.iC) {
            case 1:
                this.arrArg1 = this.strArg1.split("@");
                PaymentTokenResponse obj = gson.fromJson(this.arrArg1[0], PaymentTokenResponse.class);
                TravelGrpCreateOrderResponse obj5 = gson.fromJson(this.arrArg1[1], TravelGrpCreateOrderResponse.class);
                this.lblPaypal.setText(this.getString(R.string.end_msgs_resp) + ", " + this.arrArg1[2] + " " + this.arrArg1[3]);
                this.lblUcloudlink.setText(this.getString(R.string.msg_response) + " " + obj5.data.orderSN + "," + obj.d.f);
                //this.lblPaypal.setText(this.arrArg1[0]);
                break;
            case  2:
                this.lblPaypal.setText( this.strArg1 );
                break;
            default:
                break;
        }
    }
}
