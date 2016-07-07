package th.pz.bean;

import java.io.Serializable;

public class JConfigure implements Serializable {
    /** */
    private static final long serialVersionUID = 1L;
    private String cSEQNo_A; // 总装顺序号
    private String cVinCode; // vin码
    private String cQADNo; // 天合总成号
    private String cCarType; // 车型
    private String cCarNo;
    private String tfass;
    private int index;
    private int tfassId;
    private int printSetId;
    private String chassisNumber;
    private int js;

    public int getJs() {
        return js;
    }

    public void setJs(int js) {
        this.js = js;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public JConfigure() {
    }

    public JConfigure(int index) {
        this.index = index;
    }

    public String getCSEQNo_A() {
        return cSEQNo_A;
    }

    public void setCSEQNo_A(String no_A) {
        cSEQNo_A = no_A;
    }

    public String getCVinCode() {
        return cVinCode;
    }

    public void setCVinCode(String vinCode) {
        cVinCode = vinCode;
    }

    public String getCQADNo() {
        return cQADNo;
    }

    public void setCQADNo(String no) {
        cQADNo = no;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCCarType() {
        return cCarType;
    }

    public void setCCarType(String carType) {
        cCarType = carType;
    }

    public String getCCarNo() {
        return cCarNo;
    }

    public void setCCarNo(String carNo) {
        cCarNo = carNo;
    }

    public int getTfassId() {
        return tfassId;
    }

    public void setTfassId(int tfassId) {
        this.tfassId = tfassId;
    }

    public String getTfass() {
        return tfass;
    }

    public void setTfass(String tfass) {
        this.tfass = tfass;
    }

    public int getPrintSetId() {
        return printSetId;
    }

    public void setPrintSetId(int printSetId) {
        this.printSetId = printSetId;
    }

}
