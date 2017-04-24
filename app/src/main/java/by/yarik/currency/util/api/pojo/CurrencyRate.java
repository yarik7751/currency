package by.yarik.currency.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

public class CurrencyRate extends SugarRecord {

    public CurrencyRate() {}

    @SerializedName("Cur_ID")
    @Expose
    private Integer curId;

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("Cur_Abbreviation")
    @Expose
    private String curAbbreviation;

    @SerializedName("Cur_Name")
    @Expose
    private String curName;

    @SerializedName("Cur_Scale")
    @Expose
    private Integer curScale;

    @SerializedName("Cur_OfficialRate")
    @Expose
    private Double curOfficialRate;

    private static List<CurrencyRate> currencies = null;
    public static void setInstance(List<CurrencyRate> _currencies) {
        currencies = _currencies;
    }
    public static List<CurrencyRate> getInstance() {
        return currencies;
    }

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public Integer getCurId() {
        return curId;
    }

    public void setCurId(Integer curId) {
        this.curId = curId;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public Double getCurOfficialRate() {
        return curOfficialRate;
    }

    public void setCurOfficialRate(Double curOfficialRate) {
        this.curOfficialRate = curOfficialRate;
    }

    public static List<CurrencyRate> getCurrencies() {
        return currencies;
    }

    public static void setCurrencies(List<CurrencyRate> currencies) {
        CurrencyRate.currencies = currencies;
    }

    public Integer getCurScale() {
        return curScale;
    }

    public void setCurScale(Integer curScale) {
        this.curScale = curScale;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
