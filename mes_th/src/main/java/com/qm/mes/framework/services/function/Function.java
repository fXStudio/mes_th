package com.qm.mes.framework.services.function;
public class Function 
{
	//功能id
	private String functionid;
	//功能名
	private String functionname;
	//节点类型
	private String nodeType;
	//文件URL
	private String url;
	//安全访问标记
	private String safemark;
	//层
	private String level;
	//父节点
	private String upfunctionid;
	//状态
	private String state;
	//级别
	private String rank;
	//备注
	private String note;
	
	public Function(){
		
	}
	
	public String getFunctionID()
	{
		return this.functionid;
	}
	public String getFunctionName()
	{
		return this.functionname;
	}
	public String getNodeType()
	{
		return this.nodeType;
	}
	public String getURL()
	{
		return this.url;
	}
	public String getUpFunctionID()
	{
		return this.upfunctionid;
	}
	public String getSafeMark()
	{
		return this.safemark;
	}
	public String getLevel()
	{
		return this.level;
	}
	public String getState()
	{
		return this.state;
	}
	public String getNote()
	{
		return this.note;
	}
	public String getRank()
	{
		return this.rank;
	}
	//=======================================

	public void setFunctionID(String functionid)
	{
		this.functionid=functionid;
	}
	public void setFunctionName(String functionname)
	{
		this.functionname=functionname;
	}
	public void setNodeType(String nodetype)
	{
		this.nodeType=nodetype;
	}
	public void setURL(String url)
	{
		this.url=url;
	}
	public void setUpFunctionID(String upfunctionid)
	{
		this.upfunctionid=upfunctionid;
	}
	public void setLevel(String level)
	{
		this.level=level;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public void setSafeMark(String s)
	{
		this.safemark=s;
	}
	public void setRank(String s)
	{
		this.rank=s;
	}
	public void setNote(String s)
	{
		this.note=s;
	}
}