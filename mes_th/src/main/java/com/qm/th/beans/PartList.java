package com.qm.th.beans;

/**
 * PartList表
 * 
 * @author Gaohf
 * @date 2010-4-19
 */
public class PartList {
    /** 索引 */
    private Integer id;

    /** 零件号 */
    private String cpartno;

    /** 零件类别 */
    private String cparttype;

    /** 天合总成名 */
    private String ctfassname;

    /** 备注 */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpartno() {
        return cpartno;
    }

    public void setCpartno(String cpartno) {
        this.cpartno = cpartno;
    }

    public String getCtfassname() {
        return ctfassname;
    }

    public void setCtfassname(String ctfassname) {
        this.ctfassname = ctfassname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCparttype() {
        return cparttype;
    }

    public void setCparttype(String cparttype) {
        this.cparttype = cparttype;
    }
}
