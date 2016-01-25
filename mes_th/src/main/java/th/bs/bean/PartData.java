package th.bs.bean;

/**
 * BOM表
 * 
 * @author Gaohf
 * @date 2010-4-19
 */
public class PartData {
    /** 主键 */
    private Integer id;

    /** 天合零件号 */
    private String ctfass;

    /** 大众主零件号 */
    private String cvwmainpart;

    /** 大众子零件种类数 */
    private Integer nvwsubparttype;

    /** 大众子零件号 */
    private String cvwsubpart;

    /** 大众子零件数量 */
    private Integer nvwsubpartquan;

    /** 方案号 */
    private String cqadno;

    /** 过度车型 */
    private String cistempcar;

    /** 备注 */
    private String cremark;

    public String getCtfass() {
        return ctfass;
    }

    public void setCtfass(String ctfass) {
        this.ctfass = ctfass;
    }

    public String getCvwmainpart() {
        return cvwmainpart;
    }

    public void setCvwmainpart(String cvwmainpart) {
        this.cvwmainpart = cvwmainpart;
    }

    public Integer getNvwsubparttype() {
        return nvwsubparttype;
    }

    public void setNvwsubparttype(Integer nvwsubparttype) {
        this.nvwsubparttype = nvwsubparttype;
    }

    public String getCvwsubpart() {
        return cvwsubpart;
    }

    public void setCvwsubpart(String cvwsubpart) {
        this.cvwsubpart = cvwsubpart;
    }

    public Integer getNvwsubpartquan() {
        return nvwsubpartquan;
    }

    public void setNvwsubpartquan(Integer nvwsubpartquan) {
        this.nvwsubpartquan = nvwsubpartquan;
    }

    public String getCqadno() {
        return cqadno;
    }

    public void setCqadno(String cqadno) {
        this.cqadno = cqadno;
    }

    public String getCistempcar() {
        return cistempcar;
    }

    public void setCistempcar(String cistempcar) {
        String isTemp = "T".equalsIgnoreCase(cistempcar) ? "是" : "否";
        this.cistempcar = isTemp;
    }

    public String getCremark() {
        return cremark;
    }

    public void setCremark(String cremark) {
        this.cremark = cremark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}