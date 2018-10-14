package mx.dees.gopiemex.payment;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mx.dees.gopiemex.resources.TravelUtility;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*

import com.paypal.api.openidconnect.Session;
import com.paypal.api.openidconnect.Tokeninfo;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.exception.PayPalException;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.exception.*;
import com.paypal.base.exception.PayPalException.*;*/


/**
 * Created by leon on 15/03/18.
 */

public class PaymentRequest {


    private Activity act = null;
    private String strRe = "something went wrong, please call to support team! thank you";
    private Response response = null;
    private Request request = null;
    private OkHttpClient client = null;
    private MediaType mt = MediaType.parse(TravelUtility.strMediaType);
    private Gson gson = new Gson();
    //public static final String strCreateTokenPayment = "https://api.sandbox.paypal.com/v1/oauth2/token";
    //public static final String clientId = "AWWWz6a2cLMKzlmfnoj0uReD9P9zIOHcaTdPIMURchMyfIs3FYVbcdlNeMqmv78vDzsQ9wx3jRTXIH1c";
    //public static final String clientSecret = "EAN691dWdRw3geBVGQlVHCj6RVrRfXZH2PW--uoZTgOcpcc6PvBdSf3BzwJ17PJDsbLuNCM_k3bVI0Xt";

    public PaymentRequest(Activity act)
    {
        this.act =act;
    }

    public String CreateToken()
    {
        return  "";
    }

    /*public String CreateToken()
    {
        try {


            System.out.println("GoPie: PaymentResquest CreateToken: 1");
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("1.00");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://dees.mx/travel");
        redirectUrls.setReturnUrl("https://dees.mx/travel");
        payment.setRedirectUrls(redirectUrls);


            APIContext apiContext = new APIContext(clientId,clientSecret,"sandbox");
            System.out.println("GoPie: PaymentResquest CreateToken 2: "+ gson.toJson(apiContext));


            List<String> scopes = new ArrayList<String>() {{

                add("openid");
                add("profile");
                add("email");
            }};
            String redirectUrl = Session.getRedirectURL("UserConsent", scopes, apiContext);
            System.out.println("GoPie: PaymentResquest CreateToken 2.1:"+redirectUrl);

            Tokeninfo info = Tokeninfo.createFromAuthorizationCode(apiContext, redirectUrl);
            String accessToken = info.getAccessToken();
            String refreshToken = info.getRefreshToken();

            //Payment createdPayment = payment.create(apiContext);
            System.out.println("GoPie: PaymentResquest CreateToken 3: "+ gson.toJson(apiContext) + refreshToken );

            return "" ;//createdPayment.toString();
        } catch (PayPalRESTException e) {
            System.out.println("GoPie: PaymentResquest CreateToken error PayPalRESTException: " + gson.toJson(e));
            return e.toString();
        } catch (Exception ex) {
            System.out.println("GoPie: PaymentResquest CreateToken error Exception: " +gson.toJson(ex));
            return ex.toString();
        }
    }*/


}
