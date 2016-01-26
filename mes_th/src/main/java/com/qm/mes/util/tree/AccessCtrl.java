package com.qm.mes.util.tree;

import java.sql.*;

import com.qm.th.security.MD5;

public class AccessCtrl
{
	public String message="";
	private DataServer_UserManage ds=null;
	
	public AccessCtrl(Connection con)throws Exception
	{
		ds=new DataServer_UserManage(con);
	}
	/*
	 * 用户密码验证
	 */
	public boolean userProof(String username,String password)
	{
		MD5 m = new MD5();
		String password_32=m.getMD5ofStr(password);
		try{
			if(!ds.getExistUser(username))
			{
				this.message="用户名不存在！";
				return false;
			}
			String userid=ds.getUserID(username);
			String state=ds.getUserState(userid);
			if(state.trim().equals("0"))
			{
				this.message="该用户已被停用！";
				return false;
			}
			if(!ds.userProof(username,password_32))
			{
				this.message="密码不正确！";
				return false;
			}
			return true;
		}catch(Exception e)
		{
			System.out.println("AccessCtrl类userProof(String userid,String username,String password)方法抛出异常！"+e);
			this.message="发生异常:"+e.getMessage();
			return false;
		}
	}
	public String getMessage()
	{
		return this.message;
	}
}
