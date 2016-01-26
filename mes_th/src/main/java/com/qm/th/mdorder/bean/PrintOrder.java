package com.qm.th.mdorder.bean;

import java.util.ArrayList;
import java.util.List;

import com.qm.th.beans.JConfigure;

/**
 * ¥Ú”°≈‰÷√µ•
 * 
 * @author Ajaxfan
 */
public class PrintOrder {
	private int beginId;
	private int endId;
	private String printMd;
	private String printTitle;
	private String cCode;
	private String js;

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	private String pageNo;

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	private List<JConfigure> list = new ArrayList<JConfigure>();

	public String getPrintMd() {
		return printMd;
	}

	public void setPrintMd(String printMd) {
		this.printMd = printMd;
	}


	public int getBeginId() {
		return beginId;
	}

	public void setBeginId(int beginId) {
		this.beginId = beginId;
	}

	public int getEndId() {
		return endId;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}

	public String getPrintTitle() {
		return printTitle;
	}

	public void setPrintTitle(String printTitle) {
		this.printTitle = printTitle;
	}

	public List<JConfigure> getItems() {
		return list;
	}

	public void addItem(JConfigure item) {
		list.add(item);
	}
}
