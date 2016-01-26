package com.qm.th.bs.bean;

/**
 * 特殊零件
 * 
 * @author Gaohf
 * @date 2010-4-19
 */
public class SpecPart {
    /** 主键 */
    private Integer id;

    /** 零件 */
    private String cpart;

    /** 转换零件 */
    private String cconvertpart;

    /** 备注 */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpart() {
        return cpart;
    }

    public void setCpart(String cpart) {
        this.cpart = cpart;
    }

    public String getCconvertpart() {
        return cconvertpart;
    }

    public void setCconvertpart(String cconvertpart) {
        this.cconvertpart = cconvertpart;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
