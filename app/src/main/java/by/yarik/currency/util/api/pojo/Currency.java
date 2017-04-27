package by.yarik.currency.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "currency")
public class Currency {

    public Currency() {}

    private static List<Currency> currencies = null;
    public static void setInstance(List<Currency> _currencies) {
        currencies = _currencies;
    }
    public static List<Currency> getInstance() {
        return currencies;
    }

    @DatabaseField(generatedId = true)
    private int id;

    @SerializedName("Cur_ID")
    @Expose
	@DatabaseField()
    private Integer curId;

    @SerializedName("Cur_ParentID")
    @Expose
	@DatabaseField()
    private Integer curParentId;

    @SerializedName("Cur_Code")
    @Expose
	@DatabaseField()
    private String curCode;

    @SerializedName("Cur_Abbreviation")
    @Expose
	@DatabaseField()
    private String curAbbreviation;

    @SerializedName("Cur_Name")
    @Expose
	@DatabaseField()
    private String curName;

    @SerializedName("Cur_Name_Bel")
    @Expose
	@DatabaseField()
    private String curNameBel;

    @SerializedName("Cur_Name_Eng")
    @Expose
	@DatabaseField()
    private String curNameEng;

    @SerializedName("Cur_QuotName")
    @Expose
	@DatabaseField()
    private String curQuotName;

    @SerializedName("Cur_QuotName_Bel")
    @Expose
	@DatabaseField()
    private String curQuotNameBel;

    @SerializedName("Cur_QuotName_Eng")
    @Expose
	@DatabaseField()
    private String curQuotNameEng;

    @SerializedName("Cur_NameMulti")
    @Expose
	@DatabaseField()
    private String curNameMulti;

    @SerializedName("Cur_Name_BelMulti")
    @Expose
	@DatabaseField()
    private String curNameBelMulti;

    @SerializedName("Cur_Name_EngMulti")
    @Expose
	@DatabaseField()
    private String curNameEngMulti;

    @SerializedName("Cur_Scale")
    @Expose
	@DatabaseField()
    private Integer curScale;

    @SerializedName("Cur_Periodicity")
    @Expose
	@DatabaseField()
    private Integer curPeriodicity;

    @SerializedName("Cur_DateStart")
    @Expose
	@DatabaseField()
    private String curDateStart;

    @SerializedName("Cur_DateEnd")
    @Expose
	@DatabaseField()
    private String curDateEnd;

    public String getCurAbbreviation() {
        return curAbbreviation;
    }

    public void setCurAbbreviation(String curAbbreviation) {
        this.curAbbreviation = curAbbreviation;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) {
        this.curCode = curCode;
    }

    public String getCurDateEnd() {
        return curDateEnd;
    }

    public void setCurDateEnd(String curDateEnd) {
        this.curDateEnd = curDateEnd;
    }

    public String getCurDateStart() {
        return curDateStart;
    }

    public void setCurDateStart(String curDateStart) {
        this.curDateStart = curDateStart;
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

    public String getCurNameBel() {
        return curNameBel;
    }

    public void setCurNameBel(String curNameBel) {
        this.curNameBel = curNameBel;
    }

    public String getCurNameBelMulti() {
        return curNameBelMulti;
    }

    public void setCurNameBelMulti(String curNameBelMulti) {
        this.curNameBelMulti = curNameBelMulti;
    }

    public String getCurNameEng() {
        return curNameEng;
    }

    public void setCurNameEng(String curNameEng) {
        this.curNameEng = curNameEng;
    }

    public String getCurNameEngMulti() {
        return curNameEngMulti;
    }

    public void setCurNameEngMulti(String curNameEngMulti) {
        this.curNameEngMulti = curNameEngMulti;
    }

    public String getCurNameMulti() {
        return curNameMulti;
    }

    public void setCurNameMulti(String curNameMulti) {
        this.curNameMulti = curNameMulti;
    }

    public Integer getCurParentId() {
        return curParentId;
    }

    public void setCurParentId(Integer curParentId) {
        this.curParentId = curParentId;
    }

    public Integer getCurPeriodicity() {
        return curPeriodicity;
    }

    public void setCurPeriodicity(Integer curPeriodicity) {
        this.curPeriodicity = curPeriodicity;
    }

    public String getCurQuotName() {
        return curQuotName;
    }

    public void setCurQuotName(String curQuotName) {
        this.curQuotName = curQuotName;
    }

    public String getCurQuotNameBel() {
        return curQuotNameBel;
    }

    public void setCurQuotNameBel(String curQuotNameBel) {
        this.curQuotNameBel = curQuotNameBel;
    }

    public String getCurQuotNameEng() {
        return curQuotNameEng;
    }

    public void setCurQuotNameEng(String curQuotNameEng) {
        this.curQuotNameEng = curQuotNameEng;
    }

    public static List<Currency> getCurrencies() {
        return currencies;
    }

    public static void setCurrencies(List<Currency> currencies) {
        Currency.currencies = currencies;
    }

    public Integer getCurScale() {
        return curScale;
    }

    public void setCurScale(Integer curScale) {
        this.curScale = curScale;
    }
}
