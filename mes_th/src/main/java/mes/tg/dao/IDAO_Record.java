package mes.tg.dao;
//定义了对于采集记录的相关操作	
import mes.tg.bean.GatherRecord;

/**
 * @author chenpeng
 * 
 */
public interface IDAO_Record {

	/**
	 * 保存GatherRecord gr 实体对象
	 * 
	 * @param gr.int_gatherid
	 *            采集点id <br>
	 * @param gr.int_userid
	 *            用户id <br>
	 * @param gr.str_materielvalue
	 *            主物料标识值 <br>
	 * @param gr.str_materielname
	 *            主物料标识规则名 <br>
	 * @param gr.dat_date
	 *            过点时间
	 * @return
	 */
	String saveGatherRecord(GatherRecord gr);

	/**
	 * 通过GartherRecord gr实体对象，查询出GatherRecord的序列号
	 * 
	 * @param gr.getGatherId()
	 *            采集点id <br>
	 * @param gr.getMaterielValue()
	 *            主物料标识值 <br>
	 * 
	 * @return int_id 过点记录id<br>
	 */
	String getGatherRecordId(GatherRecord gr);

	/**
	 * 保存谱系的记录的
	 * 
	 * @param grecordId
	 *            过点记录的序号 <br>
	 * @param materielValue
	 *            子物料标识值 <br>
	 * @param materielName
	 *            子物料标识规则名 <br>
	 * @return 保存谱系的sql语句
	 */
	String savePedigreeRecord(int grecordId, String materielValue,
			String materielName);

	/**
	 * create by cp 通过信息点id(INT_GATHERID) 取得所有信息
	 * 
	 * @param grId :
	 *            采集点id
	 * @return 1、int_id 过点记录表id <br>
	 *         2、str_name 采集点名 <br>
	 *         3、str_materielvalue 主物料标识值 <br>
	 *         4、INT_USERID 用户id <br>
	 * 
	 * STR_MATERIELNAME 主物料标识规则名
	 */

	String getGatherRecordByGatherId(int grId);

	/**
	 * 通过过点记录id,取得过点记录表信息
	 * 
	 * @param id
	 *            过点记录id
	 * @return 1、STR_MATERIELVALUE 主物料标识值 <br>
	 *         2、STR_MATERIELNAME 主物料标识规则名<br>
	 *         3、int_gatherid 采集点id <br>
	 *         4、int_userid 用户id <br>
	 *         5、strDate DAT_DATE的别名,过点记录时间 <br>
	 *         6、STR_VALIDATECLASS 物料标识规则验证字符串 <br>
	 *         7、strdesc 物料标识规则验证字符串的描述信息
	 */
	String getGatherRecordById(int id);

	/**
	 * 通过过点记录id,取得谱系表信息
	 * 
	 * @param gatherrecordid
	 *            过点记录id
	 * @return 1、INT_ID 过点记录id <br>
	 *         2、STR_MATERIELVALUE 子物料标识值 <br>
	 *         3、STR_MATERIELNAME 子物料标识规则名 <br>
	 *         4、STR_VALIDATECLASS 物料标识规则验证字符串 <br>
	 *         5、strdesc 物料标识规则验证字符串的描述信息
	 */
	String getPedigreeRecordBygrid(int gatherrecordid);
	/**
	 *  修改主物料值
	 * 谢静天 
	 * @param gatherrecordid
	 *            过点记录id
	 * @param   主物料的值      
	 * 
	 */
	
    String upDateGatherRecord(int id,String mavalue);
 
	/**
	 *  修改谱系记录
	 * 谢静天 
	 * @param gatherrecordid
	 *           谱系的记录id
	 * @param   子物料的值      
	 * 
	 */
    String upDatePedigreeRecord(int id,String str_materiervalue);
    
    /**
	 *   修改谱系历史记录
	 * 谢静天 
	 * @param gatherrecordid
	 *           原来记录id
	 * @param   原来记录的值
	 *  @param  添加的原因
	 * @param  用户的id
	 */
    String savePEDIGREEHISTORY(int id,String value,String cause,String userid);
    
    String countBygatherId(int id);
   
    /**
     * 查询物料标识规则名在谱系表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public String countByMaterielRuleName(String MaterielRuleName);

    /**
     * 查询物料标识规则名在过点记录表中的数量
     * 
     * @author YuanPeng
     * @param MaterielRuleName 物料标识规则名
     * @return 数量
     */
    public String countGatherReByMaterielRuleName(String MaterielRuleName);
    
    /**
     * 	通过ID查询谱系对象
     * @author YuanPeng
     * @param id
     * @return	谱系对象
     */
    public String getPedigreeRecordById(int id);
}
