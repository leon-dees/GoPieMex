package mx.dees.gopiemex.resources;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Calendar;

import mx.dees.gopiemex.models.TravelGoodsList;
import mx.dees.gopiemex.models.TravelInput;
import mx.dees.gopiemex.models.TravelOrderItemVO;
import mx.dees.gopiemex.models.TravelWrapper;
import mx.dees.gopiemex.ucloudlink.TravelGrpCreateOrderRequest;
import mx.dees.gopiemex.ucloudlink.TravelGrpUserLoginResponse;
import mx.dees.gopiemex.ucloudlink.TravelQueryGrpOfferListRequest;
import mx.dees.gopiemex.ucloudlink.TravelQuerySubUserListInfoRequest;

/**
 * Created by leon on 7/03/18.
 */

public class TravelUtility {

    public static final String strMediaType = "application/json; charset=utf-8";
    public static final String strUrlLoginAPI = "https://saas.ucloudlink.com/bss/grp/noauth/GrpUserLogin";
    public static final String strQueryGrpOfferList = "https://saas.ucloudlink.com/bss/grp/goods/QueryGrpOfferList";
    public static final String strQuerySubUserListInfo = "https://saas.ucloudlink.com/bss/grp/user/QuerySubUserListInfo";
    public static final String strUrlLogoutAPI = "https://saas.ucloudlink.com/bss/grp/user/GrpUserLogout";
    public static final String strCreateOrder = "https://saas.ucloudlink.com/bss/grp/order/GrpCreateOrder";
    public static final String strQueryUserOfferList = "https://saas.ucloudlink.com/bss/grp/goods/QueryUserOfferList";
    //public static final String strGoPie = "http://dees.mx/travel/data/bsnsGoPie.php";
    public static final String strGoPie = "http://ec2-54-86-187-5.compute-1.amazonaws.com/travel/data/bsnsGoPie.php";
    public TravelUtility(){}

    public static String getMD5(String data)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            return e.toString();
        }
    }

    public String getImei() {

        try {
            //String imei = "";
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            String imei =  (String) (get.invoke(c, "persist.service.ext.imei"));
           //System.out.println("GoPie: TravelUtility getImei try " + imei);
            return (imei.length() > 0)? imei : "N/A";
            //return imei;
        } catch (Exception e) {
            return ""+e.toString();
            //System.out.println("GoPie: TravelUtility getImei catch " + e.toString());
        }
    }

    public static String getTravelQuerySubUserListInfo(TravelGrpUserLoginResponse tr)
    {
        Gson gson = new Gson();
        TravelQuerySubUserListInfoRequest obj = new TravelQuerySubUserListInfoRequest();
        obj.loginCustomerId = tr.data.userId;
        obj.streamNo = tr.streamNo;
        obj.partnerCode = "TRAVELCONN";
        obj.langType = "en-US";
        obj.currentPage = 1;
        obj.perPageCount = 10;
        obj.deleteFlag = 1;
        return gson.toJson(obj);
    }

    public static String getToken(TravelGrpUserLoginResponse tr, String categoryCode)
    {
        Gson gson = new Gson();
        TravelQueryGrpOfferListRequest obj = new TravelQueryGrpOfferListRequest();
        obj.loginCustomerId = tr.data.userId;
        obj.streamNo = tr.streamNo;
        obj.partnerCode = "TRAVELCONN";
        obj.langType = "en-US";
        obj.categoryCode = categoryCode;
        obj.currentPage = 1;
        obj.perPageCount = 10;
        obj.channelType = "GRP";
        obj.goodsType="PKAG";
        return gson.toJson(obj);
    }

    public static String getCreateOrder(TravelGrpUserLoginResponse tr, TravelOrderItemVO goods, TravelWrapper users){
        Gson gson = new Gson();
        TravelGrpCreateOrderRequest obj = new TravelGrpCreateOrderRequest();
        obj.streamNo = tr.streamNo;
        obj.partnerCode = "TRAVELCONN";
        obj.loginCustomerId = tr.data.userId;//users.data.dataList[0].customerId
        obj.userCode = users.data.dataList[1].userCode;
        obj.channelType = "GRP";
        obj.orderType = "BUYPKG";
        obj.goodsList= new TravelOrderItemVO[] {goods};
        obj.orderMark = "test";
        obj.currencyType = "USD";
        obj.payMethod = "ACCOUNT_AMOUNT";
        return gson.toJson(obj);
    }

    public static String submitCreateOrder(String streamNo, String userId, String goodsId, String userCode){
        Gson gson = new Gson();
        Long millis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.add(Calendar.HOUR, 23);
        TravelGrpCreateOrderRequest obj = new TravelGrpCreateOrderRequest();
        TravelOrderItemVO obj1 = new TravelOrderItemVO();

        obj1.quanity = "1";
        obj1.goodsId = goodsId;
        obj1.effectiveTime = millis / 1000L;
        obj1.expireTime = calendar.getTimeInMillis() / 1000L;

        obj.streamNo = streamNo;
        obj.partnerCode = "TRAVELCONN";
        obj.loginCustomerId = userId;
        obj.userCode = userCode;
        obj.channelType = "GRP";
        obj.orderType = "BUYPKG";
        obj.goodsList= new TravelOrderItemVO[] {obj1};
        obj.orderMark = "test";
        obj.currencyType = "USD";
        obj.payMethod = "ACCOUNT_AMOUNT";
        return gson.toJson(obj);
    }

    public static String getInputParameter(TravelGrpUserLoginResponse tr)
    {
        Gson gson = new Gson();
        TravelInput obj = new TravelInput();
        obj.streamNo = tr.streamNo;
        obj.partnerCode ="TRAVELCONN";
        obj.accessToken = tr.data.accessToken;
        obj.loginCustomerId  = tr.data.userId;
        obj.langType = "en-US";
        return gson.toJson(obj);
    }

    public static TravelOrderItemVO getItemsVO(TravelGoodsList goods)
    {
        Long millis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.add(Calendar.HOUR, 23);
        TravelOrderItemVO obj = new TravelOrderItemVO();
        obj.goodsId = goods.goodsId;
        obj.quanity = "1";
        obj.effectiveTime = millis / 1000L;
        obj.expireTime = calendar.getTimeInMillis() / 1000L;
        return obj;
    }
}
