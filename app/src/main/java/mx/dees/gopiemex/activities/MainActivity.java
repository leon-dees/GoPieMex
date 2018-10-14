package mx.dees.gopiemex.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.dees.gopiemex.R;
import mx.dees.gopiemex.dataservice.AccountDataTask;
import mx.dees.gopiemex.models.GoodsListGoPie;
import mx.dees.gopiemex.resources.LocaleHelper;
import mx.dees.gopiemex.resources.TravelTask;
import mx.dees.gopiemex.resources.TravelUtility;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginRequest;

public class MainActivity extends Activity {

    private Gson gson = new GsonBuilder().create();
private TextView lblUserGoPie = null;
private Button btnLang = null;
    private Spinner spiLang = null;
    private String strLang = "en";
    private ArrayAdapter<String> mAdapter = null;
    private TextView lblCredits = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lblUserGoPie = findViewById(R.id.lblUserGoPie);
        this.spiLang = findViewById(R.id.spiLang);
        this.btnLang = findViewById(R.id.btnLang);
        this.lblCredits = findViewById(R.id.lblCredits);
        this.mAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.lang_select));
        this.spiLang.setAdapter(this.mAdapter);
        if (LocaleHelper.getLanguage(MainActivity.this).equalsIgnoreCase("es")) {
            this.spiLang.setSelection(mAdapter.getPosition("Español"));
        } else if (LocaleHelper.getLanguage(MainActivity.this).equalsIgnoreCase("en")){
            this.spiLang.setSelection(mAdapter.getPosition("English"));
        }
        else {
            this.spiLang.setSelection(mAdapter.getPosition("App info"));
        }


        this.spiLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i) {
                    case 2:
                        strLang = "es";
                        break;
                    case 1:
                        strLang = "en";
                        break;
                    default:
                        strLang = "en";
                        lblCredits.setVisibility(View.VISIBLE);
                        break;
                }
                System.out.println("GoPie: MainActivity onItemSelected " + strLang);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    /*public void setLangRecreate(String langval) {
        LocaleHelper.setLocale(MainActivity.this,langval);
        recreate();
        System.out.println("GoPie: MainActivity setLangRecreate: " + langval);
    }*/

    public void btnLang(View v)
    {
        LocaleHelper.setLocale(MainActivity.this,this.strLang);
        recreate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        AccountDataTask obj3 = new AccountDataTask(this);
        obj3.execute(new String[]{"gopie_ucloudlink"});
    }

    public void btnDaypass(View v)
    {
        TravelGrpUserLoginRequest[] obj1 = this.gson.fromJson(this.lblUserGoPie.getText().toString(), TravelGrpUserLoginRequest[].class);
        Intent intent = new Intent(this, ActivateActivity.class);
        intent.putExtra("arg1", this.gson.toJson(obj1[0]));
        Log.v("LogGopie","MainActivity btnDaypass lblUserGopie:"+this.lblUserGoPie.getText().toString());
        startActivity(intent);
    }

}
