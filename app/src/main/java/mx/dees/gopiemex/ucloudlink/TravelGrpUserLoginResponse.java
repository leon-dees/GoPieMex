package mx.dees.gopiemex.ucloudlink;

import mx.dees.gopiemex.models.TravelSession;

/**
 * Created by leon on 7/03/18.
 */

public class TravelGrpUserLoginResponse {
    public String streamNo = "";
    public String resultCode = "";
    public String resultDesc ="";
    public TravelSession data = new TravelSession();
    public TravelGrpUserLoginResponse(){}
}
