package th.servlet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import th.pz.bean.JConfigure;

import common.Conn_MES;
/*
 * 打印说明：该文件用来处理重复打印。
 * 首次打印，系统将数据插入到print_data表中。
 * 再打印已经打印过的数据时，用此文件处理。
 * 打印信息取自print_data表。
 */
public class mPrintServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		Connection con =null;
		//String ls = "";
		//需要写活printId 和groupId
		//String groupId="8";
		//String printId="";
		
		String rq = "";
		String ch = "";
		String  js = "";
		String groupId="";
		//组合ID
		groupId=request.getParameter("groupid");
		//System.out.println(groupId);
		//ls = request.getParameter("ls");
		//日期
		rq = request.getParameter("rq");
		//车号
		ch = request.getParameter("ch");
		//System.out.println("ccccccccccccccccc"+ch);
		//第几份打印
		js = request.getParameter("js");
		//System.out.println("js is :"+js);
		
		
		Statement stmt =null;
		Statement insert_stmt = null;
		Statement insert_c = null;
		Statement x_stmt = null;
		Statement v_stmt =  null;
		Statement ps_stmt = null;
		ResultSet rs = null;
		ResultSet rs_c = null;
		ResultSet x_rs = null;
		ResultSet v_rs = null;
		//List list =new ArrayList();
		//List<JConfigure> list =new ArrayList<JConfigure>();
		
		//JConfigure
		int index =0;
		
		String insert_sql = "";
		String x_sql = "";
		String sql ="";
		String c_sql=""; 
		String v_sql="";
		
		int zhid ;            //组合id
		String dh = "";       //单号
		/*
		 * 配货单号定义规则 两位年+两位月+两位日+printSet表中的groupId(两位）+四位流水号 共12位
		 */
		String dysj = "";   //打印时间
		int cjs=0 ;             //车架数
		int xh ;              //序号
		String vincode = "";  //vin码
		String sxh = "";      //顺序号
		int zcid;             //天合总成名称id
		int cls ;             //车辆数
		String bz ="";        //备注
		String kinh = "";     //kin号
		
		String tFass="";	//天合零件号
		String tFassName="";  //天合总成名称
		int tFassCount=0;	//每页打印数量
		String carType=""; //车型代码
		String seq_A=""; //总装顺序号
		String mesSeq=""; //配货单顺序号
		String factoryNo=""; //工厂号；
		String printMd="";   //打印模版
		String strSql="";
		String sqlPrint=""; //where
		String pageNo="0"; //print_data.cpageNo单号
		String printRadio="0"; //打印Radio所选1一次2两次3四次
		String printSetCode=""; //用来区别安全气囊、方向盘
		String printTitle="";  //打印标题
		ResultSet printSet_rs=null;
		Statement printSet_stmt=null;
		List<List<JConfigure>> superlist = new ArrayList<List<JConfigure>>();
		Map<String,Object>  parameters = new HashMap<String,Object>(); //ireport 传参数用			
		String printTm[];
		
		int printDsCount=0; //相同groupid的记录数
		//int listCount=0;  //数组list值；
		
		
		int indexx=0;
		//系统日期
		Calendar ca = Calendar.getInstance();
		Date da = ca.getTime();
		SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String  sj =  formt.format(da);
	
		try{
			Conn_MES conmes = new Conn_MES();
			con  = conmes.getConn();

			//定义数组
			strSql="select count(*) from printSet where iPrintGroupId="+groupId;
			//System.out.println("SSSSSSSSSSSSSSSSSS"+strSql);
			printSet_stmt=con.createStatement();
			printSet_rs=printSet_stmt.executeQuery(strSql);
			if(printSet_rs.next())
			{
				printDsCount=printSet_rs.getInt(1);
				//listCount=printDsCount;
			}	
			
			String printId[];
			printId=new String[printDsCount];
			printTm=new String[printDsCount];
		
			strSql="select id,ccode from printSet where iPrintGroupId="+groupId+" order by id";
			if(printSet_rs!=null)
				printSet_rs.close();
			if(printSet_stmt!=null)
				printSet_stmt.close();	
			printSet_stmt=con.createStatement();
			printSet_rs=printSet_stmt.executeQuery(strSql);
			
			int iiPrintId=0;
			//int iiList=0;

			while (printSet_rs.next())
			{
				//System.out.println("ssssssss"+iiPrintId);
				printSetCode=printSet_rs.getString(2).trim();
				printId[iiPrintId]=printSet_rs.getString(1);
				superlist.add(new ArrayList<JConfigure>());
								
			 //过滤打印设置完的数据	
			v_sql = "select ctfassname,cCarType,ntfassCount,dPrintDate,cSEQNo_A,iMesSeq,cFactory,cPrintMd,cPrintRadio,ccartypedesc  from printSet where id="+printId[iiPrintId];
			//System.out.println(v_sql);
			v_stmt  = con.createStatement();
			v_rs = v_stmt.executeQuery(v_sql);
			
			if(v_rs.next()){
				
				tFassName=v_rs.getString(1).trim();
				carType=v_rs.getString(2).trim();
				tFassCount=v_rs.getInt(3);
				seq_A=v_rs.getString(5).trim();
				mesSeq=v_rs.getString(6).trim();
				factoryNo=v_rs.getString(7).trim();
				printMd=v_rs.getString(8).trim();
				printRadio=v_rs.getString(9).trim();
				printTitle=v_rs.getString(10).trim();

				//自动生产配货单号 2年2月2日2printid4流水号
				dh=rq.substring(2,4);
				dh=dh+rq.substring(5,7);
				dh=dh+rq.substring(8,10);			
				//printid
				String tempPrintId=printId[iiPrintId];			
				int  printLength = tempPrintId.length();
				for (int ii=0;ii<(2-printLength);ii++)
				{
					tempPrintId="0"+tempPrintId;
					//System.out.println(ii+":"+seq_A);		
				}			
				dh=dh+tempPrintId;
				//流水号				
				if(printRadio.equals("1"))
					printRadio="1";
				if(printRadio.equals("2"))
					printRadio="2";
				if(printRadio.equals("3"))
					printRadio="4";
				
				String tempSeq=String.valueOf(Integer.valueOf(mesSeq)-(Integer.valueOf(printRadio)-Integer.valueOf(js)));
				//System.out.println("mesSeq is :"+mesSeq);
				//System.out.println("printRadio is :"+printRadio);
				//System.out.println("js is :"+js);			
				//System.out.println("tempSeq is :"+tempSeq);
				int  tempLength = tempSeq.length();
				//tempSeq=String.valueOf(Integer.valueOf(tempSeq)+1);
				for (int ii=0;ii<(4-tempLength);ii++)
				{
					tempSeq="0"+tempSeq;
					//System.out.println(ii+":"+seq_A);		
				}
				
				dh=dh+tempSeq;
				printTm[iiPrintId]=dh;
				//System.out.println("dh is :"+dh);
				//打印单号生成完毕。
				//select  c.cSEQNo_A,c.cVinCode,c.cCarType,cQADNo,sc.ITFASSNameId,sc.iTFASSNum,c.cCarNo from carData c,carData_D sc,TFASSName t
				sqlPrint="select  cseqno,cvincode,cTFAss,itfassnameid,cKinNo,iCarNo,inum,b.ccode from print_data a left outer join kinset b on substring(a.ckinno,6,1)=b.csubkin where cPageNo='"+dh+"' " +
						"order by inum";
				
				System.out.println("sqlllllllllllllllllll"+sqlPrint);
				//System.out.println("WWWWWWWWWWWWWWWWwww"+sqlWhere);
				//System.out.println("ssssssssssssssssssssssssql:"+sqlWhere);
				
				insert_stmt = con.createStatement();
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				rs = stmt.executeQuery(sqlPrint);
				index=0;
				indexx=0;
				while(rs.next()){
					JConfigure jc =new JConfigure();
					//System.out.println("jjjjjjjjjjjjjjc"+rs.getString("cQADNo"));				
					//jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
					jc.setCSEQNo_A(rs.getString(1).trim());
					//jc.setCVinCode(rs.getString("cVinCode"));
					jc.setCVinCode(rs.getString(2).trim());
					jc.setCCarNo(rs.getString(5).trim());
					//jc.setCQADNo(rs.getString("cQADNo"));
					
					if(rs.getString(3).trim().equals("null"))
						jc.setCQADNo(" ");
					else
						jc.setCQADNo(rs.getString(3).trim());
					
					
					if(printSetCode.equals("1")&&tFassName.trim().equals("方向盘"))
					{
						jc.setCCarType("FXP");
					}
					else if(printSetCode.equals("1")&&tFassName.trim().equals("安全气囊"))
					{
						jc.setCCarType("AQQN");
					}
					else
					{
						jc.setCCarType(rs.getString(8).trim());
					}
					
					//jc.setCCarType(rs.getString("cCarType"));
					//jc.setCCarType(rs.getString("cCarType"));
					
					//从1开始， 对应数据库iNum
					index++;
					
					//从0开始，正管打印用
					indexx++;
					
					jc.setIndex(rs.getInt(7));
					//list.add(jc);
					//System.out.println("jcjcjjcjcjc"+jc.getCCarType());
					superlist.get(iiPrintId).add(jc);
					
					//System.out.println("jccccccccccc"+jc.getCQADNo());
					/*
					vincode = rs.getString("cVinCode");//vin 
					sxh = rs.getString("cSEQNo_A");//顺序号
					zcid = rs.getInt("ITFASSNameId");//总成id 
					//cls = rs.getInt("iTFASSNum");//车辆数
					dysj= formt.format(da);     	// 打印时间
					kinh = rs.getString("cCarNo"); //kin号
					tFass=rs.getString("cQADNo"); //天合零件号
					*/
					sxh = rs.getString(1).trim();//顺序号
					vincode = rs.getString(2).trim();//vin 
					tFass=rs.getString(3).trim(); //天合零件号
					zcid = rs.getInt(4);//总成id 
					//cls = rs.getInt("iTFASSNum");//车辆数
					dysj= formt.format(da);     	// 打印时间
					kinh = rs.getString(5).trim(); //kin号	
					cjs=rs.getInt(6);  //车架数
		
				}	
				
				
			}
				
			else
			{
				System.out.println("************printSet表中没有id为"+printId[iiPrintId]+"的数据");
				
			}
			iiPrintId++;
		}//end while of printId[]
			
			/*
			 * *******************处理流程********************
			 * ****change by chenpeng 20100421**************
			 * 1、取printSet表中基础数据信息
			 * 2、从cardata表中提取一页纸能打印的数据插入到print_data表中
			 * 3、从print_Data表中取数据打印。
			 * 
			 */
			
			/*
			//1
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			sql="select ctfassname,cCarType,ntfassCount,dPrintDate,cSEQNo_A,iMesSeq from printSet where id="+printId;
			
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				tFassName=rs.getString(1).trim();
				carType=rs.getString(2).trim();
				tFassCount=rs.getInt(3);
				seq_A=rs.getString(5).trim();
				mesSeq=rs.getString(6).trim();
			}
			else
			{
				throw new Exception("printSet表没有ID为"+printId+"的数据");
			}
			
			//自动生产配货单号
			mesSeq=String.valueOf(Integer.valueOf(mesSeq)+1);
			dh=rq.substring(2,4);
			dh=dh+rq.substring(5,7);
			dh=dh+rq.substring(8,10);
	
			String tempSeq=mesSeq;
			int  tempLength = tempSeq.length();
			tempSeq=String.valueOf(Integer.valueOf(tempSeq)+1);
			for (int ii=0;ii<(4-tempLength);ii++)
			{
				tempSeq="0"+tempSeq;
				//System.out.println(ii+":"+seq_A);		
			}
			dh=dh+tempSeq;
			
			//System.out.println("rrrrrrrrr:"+dh);

			//2插入
		
			//sql="insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) ";
			//sql += "select top 6 '1','page1',getdate(),"+js+","+index+",c.cVinCode,c.cSEQNo_A,cQADNo,sc.ITFASSNameId,"+ch+",'',c.cCarNo  from carData c,carData_D sc,TFASSName t";
			//sql +=" where c.ccarno=sc.iCarId  and t.cTFASSName= '后轴' and substring(c.ccarno,6,1)='0' and sc.ITFASSNameId = t.id and convert(varchar(10),c.dABegin,20) = '"+rq+"'  order by c.cSEQNo_A";
		
			if(rs!=null)
				rs.close();
			if(stmt!=null)
				stmt.close();
			//向print_data中插入 捷达and后轴and 大于最后总装顺序号
			//print_data时间为数据库当前时间 可能需要修改。
			//inum无法自动增长
			sql="insert into print_data(iPrintGroupId,cPageNo,dPrintTime,iCarNo,inum,cVinCode,cSEQNo,cTFAss,ITFASSNameId,iBigNo,cRemark,ckinno) ";
			sql += "select top "+tFassCount+" '"+printId+"','"+dh+"',getdate(),"+js+","+index+",c.cVinCode,c.cSEQNo_A,cQADNo,sc.ITFASSNameId,"+ch+",'',c.cCarNo  from carData c,carData_D sc,TFASSName t";
			sql +=" where c.ccarno=sc.iCarId  and t.cTFASSName= '"+tFassName+"' and substring(c.ccarno,6,1)='"+carType+"' and sc.ITFASSNameId = t.id and c.cSEQNo_A>'"+seq_A+"'  order by c.cSEQNo_A";
			
			stmt = con.createStatement();		
			stmt.executeUpdate(sql);

			//3
	
			//sql += "select top 6 c.cSEQNo_A,c.cVinCode,c.cCarType,cQADNo,sc.ITFASSNameId,sc.iTFASSNum,c.cCarNo from carData c,carData_D sc,TFASSName t";
			//sql +=" where c.ccarno=sc.iCarId  and t.cTFASSName= '后轴' and substring(c.ccarno,6,1)='0' and sc.ITFASSNameId = t.id and convert(varchar(10),c.dABegin,20) = '"+rq+"'  order by c.cSEQNo_A";
			//sql="select 
			//按单号打印
			sql="select a.ctfass,a.cseqno,a.cvincode,b.cCode,a.cKinno from print_data a,kinset b where substring(a.cKinno,6,1)=b.csubKin and a.cPageNo='"+dh+"'";
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);		
			rs = stmt.executeQuery(sql);	

			if(ps_stmt!=null)
				ps_stmt.close();
			ps_stmt=con.createStatement();
			
			while(rs.next()){
				JConfigure jc =new JConfigure();
				//jc.setCQADNo(rs.getString("cQADNo"));
				jc.setCQADNo(rs.getString(1));
				//jc.setCSEQNo_A(rs.getString("cSEQNo_A"));
				jc.setCSEQNo_A(rs.getString(2));
				//jc.setCVinCode(rs.getString("cVinCode"));
				jc.setCVinCode(rs.getString(3));
				//jc.setCCarType(rs.getString("cCarType"));
				jc.setCCarType(rs.getString(4));
				//从1开始， 对应数据库iNum
				index++;
				
				//从0开始，正管打印用
				indexx++;
				
				jc.setIndex(indexx);
				list.add(jc);
				
				//vincode = rs.getString("cVinCode");//vin 
				//sxh = rs.getString("cSEQNo_A");//顺序号
				//zcid = rs.getInt("ITFASSNameId");//总成id 
				//cls = rs.getInt("iTFASSNum");//车辆数
				//dysj= formt.format(da);      // 打印时间
				//kinh = rs.getString("cCarNo"); //kin号
				
				if(rs.isLast()){
					String ps_sql = "update printSet set cLastVin='"+rs.getString(3)+"',cSEQNo_A='"+rs.getString(2)+"',cKinno='"+rs.getString(5)+"', iMESseq="+mesSeq+"   where id="+printId; 
					
					System.out.println("cccccccccccccccp:"+ps_sql);
					ps_stmt.executeUpdate(ps_sql);
				 }
			}
			//chenpeng change end
			*/
				
				// ireport x				
				iiPrintId--;
				//end

				ServletContext context = this.getServletConfig()
				.getServletContext();
				JasperPrint jasperPrint = null;
				String temp=printMd;  //打印模版在PrintSet表中 冗余存储。
				//System.out.println("ttttttttttttttttttt"+temp);
				if(iiPrintId==0)
				{
					//String temp = "ireport/jhzz.jasper";
					
					
					File reportFile = new File(context.getRealPath(temp));
					File reportFile1 = new File(context.getRealPath("ireport"));
					
					//定义datasource;
					
					//JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(superlist.get(iiPrintId));
					
					
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("js", String.valueOf(cjs));
					parameters.put("zrq", rq);
					parameters.put("ch", ch);
					parameters.put("tm", dh);
					//个性参数
					parameters.put("mc", printTitle);
					parameters.put("id", Integer.valueOf(printId[iiPrintId]));
					parameters.put("x_sub", reportFile1.getPath()+"\\");
					parameters.put("xdir", reportFile1.getPath()+"\\");			
					
					
					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					//System.out.println(iiPrintId);
					//superlist为原来的list 。
	
					//jasperPrint = JasperFillManager.fillReport(jasperReport,
							//parameters, datasource);
					/*
					System.out.println("sss"+superlist.get(0).get(0).getCQADNo());
					System.out.println(iiPrintId);
					System.out.println("superrrrrrrrrrrrrrrrrr:"+superlist.get(iiPrintId).size());
					System.out.println(parameters);
					System.out.println(con);
					System.out.println(jasperReport);
					System.out.println(reportFile.getPath());
					*/
					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, new JRBeanCollectionDataSource(superlist.get(iiPrintId)));
				}
				else if(printSetCode.equals("1"))
				{
					if(printSet_rs!=null)
						printSet_rs.close();
					if(printSet_stmt!=null)
						printSet_stmt.close();
					List fxpqnList=new ArrayList();
					strSql="select cCode,ctfassname,id from printSet where iPrintGroupId="+groupId+" order by id";
					printSet_stmt=con.createStatement();
					printSet_rs=printSet_stmt.executeQuery(strSql);
					//将方向盘 安全气囊list的数据付给fxpqnList
					System.out.println("iiiiiiiiii"+iiPrintId);
					for (int iitFassCount=0;iitFassCount<tFassCount;iitFassCount++)
					{	
						for (int iifxp=0;iifxp<=iiPrintId;iifxp++)
						{
							fxpqnList.add(superlist.get(iifxp).get(iitFassCount));
							System.out.println(superlist.get(iifxp).get(iitFassCount));
							System.out.println("seqno_a:"+superlist.get(iifxp).get(iitFassCount).getCSEQNo_A());
							System.out.println("iifxp:"+iifxp);
						}
					}
					File reportFile = new File(context.getRealPath(temp));
					File reportFile1 = new File(context.getRealPath("ireport"));
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("js", String.valueOf(cjs));
					parameters.put("zrq", rq);
					parameters.put("ch", ch);
					parameters.put("tm", dh);
					//个性参数
					//方向盘 /安全气囊
					parameters.put("mc", "奥迪B8/Q5 方向盘/安全气囊");
					parameters.put("id", Integer.valueOf(printId[iiPrintId]));
					parameters.put("x_sub", reportFile1.getPath()+"\\");
					parameters.put("xdir", reportFile1.getPath()+"\\");			
					
					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, new JRBeanCollectionDataSource(fxpqnList));
				}
				else
				{
					
					//Map<String,Object> parameters = new HashMap<String,Object>();
					
					if(printSet_rs!=null)
						printSet_rs.close();
					if(printSet_stmt!=null)
						printSet_stmt.close();
					strSql="select cCode,ctfassname,id,cremark,ccarTypeDesc from printSet where iPrintGroupId="+groupId+" order by id";
					//System.out.println("SSSSSSSSSSSSSSSSSS"+strSql);
					printSet_stmt=con.createStatement();
					printSet_rs=printSet_stmt.executeQuery(strSql);
					int iiList=0;
					while(printSet_rs.next())
					{						
						parameters.put(printSet_rs.getString(1), new JRBeanCollectionDataSource(superlist.get(iiList)));					
						//System.out.println("ddddddddddddds"+printSet_rs.getString(1));
						//System.out.println("sizesssssssssssssssssss"+superlist.get(iiList).size());
						iiList++;
						//名称
						parameters.put("mc"+String.valueOf(iiList), printSet_rs.getString(5));
						System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmc"+String.valueOf(iiList)+","+printSet_rs.getString(2));
						parameters.put("id"+String.valueOf(iiList), printSet_rs.getInt(3));
						parameters.put("tm"+String.valueOf(iiList), printTm[iiList-1]);
						//System.out.println("iiiiiiiiiiiiiiiiii"+printSet_rs.getInt(3));
						System.out.println("IIIIIIIIIIIII,id"+String.valueOf(iiList)+","+printSet_rs.getInt(3));
						//parameters.put("JDatasource", datasource);
					//listCount=printDsCount;
					}
					//String temp = "ireport/jzxj.jasper";
		
					File reportFile = new File(context.getRealPath(temp));
					File reportFile1 = new File(context.getRealPath("ireport"));
					
					//System.out.println("ppppppppppppppppp:"+reportFile1.getPath());
					//JRBeanCollectionDataSource ydatasource = new JRBeanCollectionDataSource(listz);
					//JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);
					
					parameters.put("REPORT_CONNECTION", con);
					parameters.put("js", String.valueOf(cjs));
					parameters.put("zrq", rq);
					parameters.put("ch", ch);
					parameters.put("SUBREPORT_DIR", reportFile1.getPath()+"\\");
					parameters.put("tm", dh);
					//parameters.put("MyDatasource", ydatasource);
					//parameters.put("JDatasource", datasource);
					
					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					
					
					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, con);
				}
				
				if (jasperPrint != null) {
					response.setContentType("application/octet-stream");
					ServletOutputStream ouputStream = response.getOutputStream();

					ObjectOutputStream oos = new ObjectOutputStream(ouputStream);
					oos.writeObject(jasperPrint);
					
					oos.flush();
					oos.close();

					ouputStream.flush();
					ouputStream.close();
				} else {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out
							.println("<title>JasperReports - Web Application Sample</title>");
					out
							.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
					out.println("</head>");

					out.println("<body bgcolor=\"white\">");

					out.println("<span class=\"bold\">Empty response.</span>");

					out.println("</body>");
					out.println("</html>");
				}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally
		{   
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
	}

}
