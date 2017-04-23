package by.yarik.currency.util.api.pojo;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

public class Currency extends SugarRecord {

    public Currency() {}

    private static List<Currency> currencies = null;
    public static void setInstance(List<Currency> _currencies) {
        currencies = _currencies;
    }
    public static List<Currency> getInstance() {
        return currencies;
    }

    @SerializedName("Cur_ID")
    @Expose
    private String curId;

    @SerializedName("Cur_ParentID")
    @Expose
    private String curParentId;

    @SerializedName("Cur_Code")
    @Expose
    private String curCode;

    @SerializedName("Cur_Abbreviation")
    @Expose
    private String curAbbreviation;

    @SerializedName("Cur_Name")
    @Expose
    private String curName;

    @SerializedName("Cur_Name_Bel")
    @Expose
    private String curNameBel;

    @SerializedName("Cur_Name_Eng")
    @Expose
    private String curNameEng;

    @SerializedName("Cur_QuotName")
    @Expose
    private String curQuotName;

    @SerializedName("Cur_QuotName_Bel")
    @Expose
    private String curQuotNameBel;

    @SerializedName("Cur_QuotName_Eng")
    @Expose
    private String curQuotNameEng;

    @SerializedName("Cur_NameMulti")
    @Expose
    private String curNameMulti;

    @SerializedName("Cur_Name_BelMulti")
    @Expose
    private String curNameBelMulti;

    @SerializedName("Cur_Name_EngMulti")
    @Expose
    private String curNameEngMulti;

    @SerializedName("Cur_Scale")
    @Expose
    private String curScale;

    @SerializedName("Cur_Periodicity")
    @Expose
    private String curPeriodicity;

    @SerializedName("Cur_DateStart")
    @Expose
    private String curDateStart;

    @SerializedName("Cur_DateEnd")
    @Expose
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

    public String getCurId() {
        return curId;
    }

    public void setCurId(String curId) {
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

    public String getCurParentId() {
        return curParentId;
    }

    public void setCurParentId(String curParentId) {
        this.curParentId = curParentId;
    }

    public String getCurPeriodicity() {
        return curPeriodicity;
    }

    public void setCurPeriodicity(String curPeriodicity) {
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

    public String getCurScale() {
        return curScale;
    }

    public void setCurScale(String curScale) {
        this.curScale = curScale;
    }
}
