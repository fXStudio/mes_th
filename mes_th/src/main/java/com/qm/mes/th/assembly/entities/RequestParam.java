package com.qm.mes.th.assembly.entities;

/**
 * 数据请求参数
 * 
 * @author Ajaxfan
 */
public class RequestParam {
    /** 组号 */
    private String groupId;
    /** 日期 */
    private String requestDate;
    /** 底盘号 */
    private String chassisNumber;
    /** 架子号 */
    private String js;

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }
}
