package mx.dees.gopiemex.models;

/**
 * Created by leon on 7/03/18.
 */

public class TravelToken {
    public String streamNo = "";
    public String resultCode ="";
    public String resultDesc = "";
    public TravelSession data = new TravelSession();
    public TravelToken(){}
}
