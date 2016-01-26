/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.ra.bean;

import java.util.Date;
/**
 *
 * @author YuanPeng
 */
public class Instruction  implements java.io.Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * 序号
 */
private int id ;
/**
 * 生产单元
 */
private int produnitid ;
/**
 * 生产日期
 */
private Date produceDate ;
/**
 * 版本号
 */
private String versionCode ;
/**
 * 指令顺序号
 */
private int instructionOrder ;
/**
 * 计划日期
 */
private Date planDate ;
/**
 * 计划顺序号
 */
private int planOrder ;
/**
 * 产品类别标识
 */
private String produceType ;
/**
 * 产品名称
 */
private String produceName ;
/**
 * 产品标识
 */
private String produceMarker ;
/**
 * 班次
 */
private String workOrder ;
/**
 * 班组
 */
private String workTeam ;
/**
 * 预计开始时间
 */
private Date planBegin ;
/**
 * 预计完成时间
 */
private Date planFinish ;
/**
 * 数量
 */
private int count ;
/**
 * 指令状态id
 */
private int instructStateID ;
/**
 * 指令发布标识
 */
private int issuance ;
/**
 * 状态跳转的异常标识
 */
private int StaError ;
/**
 * 指令被删除标识
 */
private int delete ;
private int edit;
    public int getEdit() {
	return edit;
}

public void setEdit(int edit) {
	this.edit = edit;
}

	/**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the produceDate
     */
    public Date getProduceDate() {
        return produceDate;
    }

    /**
     * @param produceDate the produceDate to set
     */
    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    /**
     * @return the versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return the instructionOrder
     */
    public int getInstructionOrder() {
        return instructionOrder;
    }

    /**
     * @param instructionOrder the instructionOrder to set
     */
    public void setInstructionOrder(int instructionOrder) {
        this.instructionOrder = instructionOrder;
    }

    /**
     * @return the planDate
     */
    public Date getPlanDate() {
        return planDate;
    }

    /**
     * @param planDate the planDate to set
     */
    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    /**
     * @return the planOrder
     */
    public int getPlanOrder() {
        return planOrder;
    }

    /**
     * @param planOrder the planOrder to set
     */
    public void setPlanOrder(int planOrder) {
        this.planOrder = planOrder;
    }

    /**
     * @return the productType
     */
    public String getProduceType() {
        return produceType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProduceType(String produceType) {
        this.produceType = produceType;
    }

    /**
     * @return the produceName
     */
    public String getProduceName() {
       return produceName;
    }

    /**
     * @param produceName the produceName to set
     */
    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    /**
     * @return the produceMarker
     */
    public String getProduceMarker() {
        return produceMarker;
    }

    /**
     * @param produceMarker the produceMarker to set
     */
    public void setProduceMarker(String produceMarker) {
        this.produceMarker = produceMarker;
    }

    /**
     * @return the workOrder
     */
    public String getWorkOrder() {
        return workOrder;
    }

    /**
     * @param workOrder the workOrder to set
     */
    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    /**
     * @return the workTeam
     */
    public String getWorkTeam() {
        return workTeam;
    }

    /**
     * @param workTeam the workTeam to set
     */
    public void setWorkTeam(String workTeam) {
        this.workTeam = workTeam;
    }

   
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the instructStateID
     */
    public int getInstructStateID() {
        return instructStateID;
    }

    /**
     * @param instructStateID the instructStateID to set
     */
    public void setInstructStateID(int instructStateID) {
        this.instructStateID = instructStateID;
    }

    /**
     * @return the issuance
     */
    public int getIssuance() {
        return issuance;
    }

    /**
     * @param issuance the issuance to set
     */
    public void setIssuance(int issuance) {
        this.issuance = issuance;
    }

    /**
     * @return the StaError
     */
    public int getStaError() {
        return StaError;
    }

    /**
     * @param StaError the StaError to set
     */
    public void setStaError(int StaError) {
        this.StaError = StaError;
    }

    /**
     * @return the delete
     */
    public int getDelete() {
        return delete;
    }

    /**
     * @param delete the delete to set
     */
    public void setDelete(int delete) {
        this.delete = delete;
    }

    /**
     * @return the planBegin
     */
    public Date getPlanBegin() {
        return planBegin;
    }

    /**
     * @param planBegin the planBegin to set
     */
    public void setPlanBegin(Date planBegin) {
        this.planBegin = planBegin;
    }

    /**
     * @return the planFinish
     */
    public Date getPlanFinish() {
        return planFinish;
    }

    /**
     * @param planFinish the planFinish to set
     */
    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    /**
     * @return the produnitid
     */
    public int getProdunitid() {
        return produnitid;
    }

    /**
     * @param produnitid the produnitid to set
     */
    public void setProdunitid(int produnitid) {
        this.produnitid = produnitid;
    }

}
