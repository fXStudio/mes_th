package mes.tg.dao;
import mes.tg.bean.NoPedigreeRecord;
/**
 * @author 谢静天
 * 
 */
public interface IDAO_NoPedigreeRecord {

	/**
	 * 保存NoPedigreeRecord noPedigreeRecord 实体对象
	 * 
	 * noPedigreeRecord.int_gatherrecord_id
	 * noPedigreeRecord.str_value
	 * noPedigreeRecord.str_gatheritemExtName
	 * @return
	 */
   public	String saveNoPedigreeRecord (NoPedigreeRecord noPedigreeRecord);
   /**
    * @param int id 
    * 通过id查询出非谱系记录的对应的所有字段值
    * @return 
    */
  
   public String getNoPedigreeRecordById(int id);
   
   /**
    * 
    * 查询出非谱系记录的所有字段值
    * @return 
    */
   public  String getAllNoPedigreeRecord ();
   

   /**
    * @param  materielvalue
    * 通过主物料的值来查询与它有关的非谱系记录。
    * @return 
    */
   public String getNoPedigreeRecordByMaterielValue(String materielvalue);

   
	/**
	 * 更新NoPedigreeRecord noPedigreeRecord 实体对象
	 * 
	 * noPedigreeRecord.int_id
	 * noPedigreeRecord.str_value
	 * noPedigreeRecord.str_gatheritemExtName
	 * @return
	 */
   
   
   public String updateNoPedigreeRecord(NoPedigreeRecord np );
   
   
   
   /**
	 * 通过过点记录表的id来查询与其有关的非谱系记录
	 * @param id
	 * 
	 * @return
	 */
  
   public String getNoPedigreeRecordBygatherid(int id);
   
}
