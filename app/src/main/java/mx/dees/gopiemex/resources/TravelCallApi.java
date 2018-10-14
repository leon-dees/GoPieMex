package mx.dees.gopiemex.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import mx.dees.gopiemex.models.TravelDataList;
import mx.dees.gopiemex.models.TravelGoodsList;
import mx.dees.gopiemex.models.TravelOrderItemVO;
import mx.dees.gopiemex.models.TravelOutput;
import mx.dees.gopiemex.models.TravelWrapper;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderRequest;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderResponse;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginResponse;
import mx.dees.gopiemex.ucloudlink.TravelQueryGrpOfferListResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leon on 7/03/18.
 */

public class TravelCallApi {
    private String strRe = "something went wrong, please call to support team! thank you";
    private Response response = null;
    private Request request = null;
    private OkHttpClient client = null;
    private MediaType mt = MediaType.parse(TravelUtility.strMediaType);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //private Gson gson = new Gson();
    public TravelCallApi(){

        this.client = new OkHttpClient();

    }

    public String GrpUserLogin(String json) throws IOException
    {
        RequestBody body = RequestBody.create(this.mt, json);
        this.request = new Request.Builder().url(TravelUtility.strUrlLoginAPI).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi GrpUserLogin 1: " + this.strRe);
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        obj = this.gson.fromJson(this.strRe, TravelGrpUserLoginResponse.class);
        this.strRe = this.gson.toJson(obj);
        //System.out.println("GoPie: TravelCallApi GrpUserLogin 2: " + this.strRe);
        return this.strRe;
    }

    public String QueryGrpOfferList(String json) throws IOException
    {
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelQueryGrpOfferListResponse obj2 = new TravelQueryGrpOfferListResponse();
        TravelDataList obj3 = new TravelDataList();
        obj = this.gson.fromJson(json, TravelGrpUserLoginResponse.class);
        this.strRe = TravelUtility.getToken(obj, "LLTC");
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strQueryGrpOfferList + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi QueryGrpOfferList response: " + this.strRe);
        obj2 = this.gson.fromJson(this.strRe, TravelQueryGrpOfferListResponse.class);
        obj3 = this.gson.fromJson(this.gson.toJson(obj2.data), TravelDataList.class);
        this.strRe = this.gson.toJson(obj3);
        System.out.println("GoPie: TravelCallApi QueryGrpOfferList dataList: " + this.strRe);
        return this.strRe;
    }

    public TravelDataList QueryGrpOfferList(String json, int c) throws IOException
    {

        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelQueryGrpOfferListResponse obj2 = new TravelQueryGrpOfferListResponse();
        TravelDataList obj3 = new TravelDataList();
        obj = this.gson.fromJson(json, TravelGrpUserLoginResponse.class);
        this.strRe = TravelUtility.getToken(obj, "LLTC");
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strQueryGrpOfferList + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();

        /*String strRe2 = TravelUtility.getToken(obj, "ZLTC");
        body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strQueryGrpOfferList + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        strRe2 = this.response.body().string();
        this.response.body().close();*/


        System.out.println("GoPie: TravelCallApi QueryGrpOfferList response: " + this.strRe);
        obj2 = this.gson.fromJson(this.strRe, TravelQueryGrpOfferListResponse.class);
        //obj2 = this.gson.fromJson(strRe2, TravelQueryGrpOfferListResponse.class);
        obj3 = this.gson.fromJson(this.gson.toJson(obj2.data), TravelDataList.class);
        this.strRe = this.gson.toJson(obj3);
        System.out.println("GoPie: TravelCallApi QueryGrpOfferList dataList: " + this.strRe);
        return obj3;
    }

    public String QuerySubUserListInfo(String json) throws IOException
    {
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelWrapper obj2 = new TravelWrapper();
        obj = this.gson.fromJson(json, TravelGrpUserLoginResponse.class);
        this.strRe = TravelUtility.getTravelQuerySubUserListInfo(obj);
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strQuerySubUserListInfo + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi QuerySubUserListInfo " + this.strRe);
        obj2 = this.gson.fromJson(this.strRe, TravelWrapper.class);
        return this.gson.toJson(obj2);
    }

    public TravelWrapper QuerySubUserListInfo(String json, int c) throws IOException
    {
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelWrapper obj2 = new TravelWrapper();
        obj = this.gson.fromJson(json, TravelGrpUserLoginResponse.class);
        this.strRe = TravelUtility.getTravelQuerySubUserListInfo(obj);
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strQuerySubUserListInfo + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi QuerySubUserListInfo " + this.strRe);
        obj2 = this.gson.fromJson(this.strRe, TravelWrapper.class);
        return obj2;
    }

    public String GrpUserLogout(String json) throws IOException
    {
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        obj = this.gson.fromJson(json, TravelGrpUserLoginResponse.class);
        this.strRe = TravelUtility.getInputParameter(obj);
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strUrlLogoutAPI + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi GrpUserLogout " + this.strRe);
        TravelOutput obj2 = new TravelOutput();
        obj2 = this.gson.fromJson(this.strRe, TravelOutput.class);
        return this.gson.toJson(obj2);
    }

    public String GrpCreateOrder(String[] json) throws IOException
    {
        TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelDataList obj2 = new TravelDataList();
        TravelWrapper obj3 = new TravelWrapper();
        TravelOrderItemVO obj4 = new TravelOrderItemVO();
        TravelGrpCreateOrderResponse obj5 = new TravelGrpCreateOrderResponse();
        obj = this.gson.fromJson(json[0], TravelGrpUserLoginResponse.class);
        obj2 = this.gson.fromJson(json[1], TravelDataList.class);
        obj4 = TravelUtility.getItemsVO(obj2.dataList[0]);
        obj3 = this.gson.fromJson(json[2], TravelWrapper.class);
        this.strRe = TravelUtility.getCreateOrder(obj,obj4,obj3);
        RequestBody body = RequestBody.create(this.mt, this.strRe);
        this.request = new Request.Builder().url(TravelUtility.strCreateOrder + "?access_token=" + obj.data.accessToken).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi GrpCreateOrder " + this.strRe);
        obj5 = this.gson.fromJson(this.strRe, TravelGrpCreateOrderResponse.class);
        return this.gson.toJson(obj5);
    }

    public String submitGrpCreateOrder(String json, String token) throws IOException
    {
        /*TravelGrpUserLoginResponse obj = new TravelGrpUserLoginResponse();
        TravelDataList obj2 = new TravelDataList();
        TravelWrapper obj3 = new TravelWrapper();
        TravelOrderItemVO obj4 = new TravelOrderItemVO();
        TravelGrpCreateOrderResponse obj5 = new TravelGrpCreateOrderResponse();
        obj = this.gson.fromJson(json[0], TravelGrpUserLoginResponse.class);
        obj2 = this.gson.fromJson(json[1], TravelDataList.class);
        obj4 = TravelUtility.getItemsVO(obj2.dataList[0]);
        obj3 = this.gson.fromJson(json[2], TravelWrapper.class);
        this.strRe = TravelUtility.getCreateOrder(obj,obj4,obj3);*/
        TravelGrpCreateOrderResponse obj5 = new TravelGrpCreateOrderResponse();
        RequestBody body = RequestBody.create(this.mt, json);
        this.request = new Request.Builder().url(TravelUtility.strCreateOrder + "?access_token=" + token).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        Gson gson2 = new Gson();
        System.out.println("GoPie: TravelCallApi GrpCreateOrder " + this.strRe);
        obj5 = gson2.fromJson(this.strRe, TravelGrpCreateOrderResponse.class);
        return gson2.toJson(obj5);
    }

    public String QueryUserOfferList(String json) throws IOException
    {
        return this.strRe;
    }

    public String setDeviceData(String json) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", json)
                .addFormDataPart("iC", "1")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi setDeviceData " + this.strRe + ", json: " + json);
        return this.strRe;
    }

    public String getAccountData(String json) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", json)
                .addFormDataPart("iC", "2")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi getAccountData " + this.strRe);
        return this.strRe;
    }

    public String setQueryGrpOfferListGoPie(String data,String pkDesc, String goodId, String timeZone, String price, String goodCode, String idDevice, String token) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", data)
                .addFormDataPart("arg2", pkDesc)
                .addFormDataPart("arg3", goodId)
                .addFormDataPart("arg4", timeZone)
                .addFormDataPart("arg5", price)
                .addFormDataPart("arg6", goodCode)
                .addFormDataPart("arg7", idDevice)
                .addFormDataPart("arg8", token)
                .addFormDataPart("iC", "3")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi setQueryGrpOfferListGoPie: " + this.strRe/* + ", " + data + ", " + pkDesc + ", " + goodId + ", " + title*/);
        return this.strRe;
    }

    public String setGrpUserLoginGoPie(String token,String idDevice, String userId, String streamNo) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", token)
                .addFormDataPart("arg2", idDevice)
                .addFormDataPart("arg3", userId)
                .addFormDataPart("arg4", streamNo)
                .addFormDataPart("iC", "4")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi setGrpUserLoginGoPie: " + this.strRe/* + ", " + data + ", " + pkDesc + ", " + goodId + ", " + title*/);
        return this.strRe;
    }

    public String setSubUserListInfoGoPie(String idDevice,String data, String customerId, String userCode, String token) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", idDevice)
                .addFormDataPart("arg2", data)
                .addFormDataPart("arg3", customerId)
                .addFormDataPart("arg4", userCode)
                .addFormDataPart("arg5", token)
                .addFormDataPart("iC", "5")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi setSubUserListInfoGoPie: " + this.strRe + ", userCode: "+userCode/* + ", " + data + ", " + pkDesc + ", " + goodId + ", " + title*/);
        return this.strRe;
    }


    public String getQueryGrpOfferListGoPie(String hash) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", hash)
                .addFormDataPart("iC", "6")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi getQueryGrpOfferListGoPie: " + this.strRe/* + ", " + data + ", " + pkDesc + ", " + goodId + ", " + title*/);
        return this.strRe;
    }

    public String setGrpCreateOrderGoPie(String imei,String data, String orderSN, String orderId, String token, String goodName, String paypalId, String paypalToken) throws IOException
    {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("arg1", imei)
                .addFormDataPart("arg2", data)
                .addFormDataPart("arg3", orderSN)
                .addFormDataPart("arg4", orderId)
                .addFormDataPart("arg5", token)
                .addFormDataPart("arg6", goodName)
                .addFormDataPart("arg7", paypalId)
                .addFormDataPart("arg8", paypalToken)
                .addFormDataPart("iC", "7")
                .build();
        this.request = new Request.Builder().url(TravelUtility.strGoPie).post(body).build();
        this.response = this.client.newCall(this.request).execute();
        this.strRe = this.response.body().string();
        this.response.body().close();
        System.out.println("GoPie: TravelCallApi setQueryGrpOfferListGoPie: " + this.strRe/* + ", " + data + ", " + pkDesc + ", " + goodId + ", " + title*/);
        return this.strRe;
    }

}
