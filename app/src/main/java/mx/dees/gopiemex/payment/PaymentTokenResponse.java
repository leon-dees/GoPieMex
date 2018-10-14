package mx.dees.gopiemex.payment;

import mx.dees.gopiemex.models.PaymentTokenC;
import mx.dees.gopiemex.models.PaymentTokenD;

/**
 * Created by leon on 15/03/18.
 */

public class PaymentTokenResponse {
    public String b = "";
    public PaymentTokenC c = new PaymentTokenC();
    public PaymentTokenD d = new PaymentTokenD();

    public PaymentTokenResponse(){}
    /*
    {
    "b":"mock",

    }

    * */
}
