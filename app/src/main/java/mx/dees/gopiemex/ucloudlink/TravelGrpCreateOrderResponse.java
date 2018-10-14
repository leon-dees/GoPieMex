package mx.dees.gopiemex.ucloudlink;

import mx.dees.gopiemex.models.TravelOrderSN;

/**
 * Created by leon on 7/03/18.
 */

public class TravelGrpCreateOrderResponse {
    public String streamNo = "";
    public String resultCode = "";
    public String resultDesc = "";
    public TravelOrderSN data = new TravelOrderSN();
    public TravelGrpCreateOrderResponse(){}
}
