package mx.dees.gopiemex.ucloudlink;

import mx.dees.gopiemex.models.TravelOrderItemVO;

/**
 * Created by leon on 7/03/18.
 */

public class TravelGrpCreateOrderRequest {
    public String streamNo = "";
    public String partnerCode = "";
    public String loginCustomerId = "";
    public String userCode = "";
    public String channelType = "";
    public String orderType = "";
    public TravelOrderItemVO[] goodsList = {};
    public String orderMark = "";
    public String currencyType = "";
    public String payMethod = "";

    public TravelGrpCreateOrderRequest(){}
}
