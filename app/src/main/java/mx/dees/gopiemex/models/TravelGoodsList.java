package mx.dees.gopiemex.models;

/**
 * Created by leon on 7/03/18.
 */

public class TravelGoodsList {
    public String goodsId = "";
    public String goodsCode = "";
    public String goodsName = "";
    public String goodsPrice = "";
    public String status = "";
    //public String[] mccList = {};
    public String mccFlag = "";
    public String createTime = "";
    public String flowByte = "";
    public String period = "";
    public String currencyType = "";
    public String categoryCode = "";
    public TravelGoodsAttr attrMap = new TravelGoodsAttr();

    public TravelGoodsList(){}
}
