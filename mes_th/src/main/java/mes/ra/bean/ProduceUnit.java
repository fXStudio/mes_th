package mes.ra.bean;

public class ProduceUnit {
	/**
	 * 序号
	 */
	private  int int_id;
	/**
	 * 生产单元名称
	 */
	private String Str_name;
	/**
	 * 生产单元编号
	 */
	private String Str_code;
	/**
	 * 未上线指令状态
	 */
    private int Int_instructStateID;
    /**
	 * 是否需要验证计划信息
	 */
    private int Int_planIncorporate;
    /**
	 * 锁定台数
	 */
    private int Int_instCount;
    /**
	 * 作废标示
	 */
    private int Int_delete;
    /**
	 * 生产单元类型
	 */
    private int Int_Type ;
    private int Int_materielRuleid;
	public int getInt_id() {
		return int_id;
	}
	public void setInt_id(int int_id) {
		this.int_id = int_id;
	}
	public String getStr_name() {
		return Str_name;
	}
	public void setStr_name(String Str_name) {
		this.Str_name = Str_name;
	}
	public String getStr_code() {
		return Str_code;
	}
	public void setStr_code(String str_code) {
		Str_code = str_code;
	}
	public int getInt_instructStateID() {
		return Int_instructStateID;
	}
	public void setInt_instructStateID(int int_instructStateID) {
		Int_instructStateID = int_instructStateID;
	}
	public int getInt_planIncorporate() {
		return Int_planIncorporate;
	}
	public void setInt_planIncorporate(int int_planIncorporate) {
		Int_planIncorporate = int_planIncorporate;
	}
	public int getInt_instCount() {
		return Int_instCount;
	}
	public void setInt_instCount(int int_instCount) {
		Int_instCount = int_instCount;
	}
	public int getInt_delete() {
		return Int_delete;
	}
	public void setInt_delete(int int_delete) {
		Int_delete = int_delete;
	}
	public int getInt_Type() {
		return Int_Type;
	}
	public void setInt_Type(int int_Type) {
		Int_Type = int_Type;
	}
	public int getInt_materielRuleid() {
		return Int_materielRuleid;
	}
	public void setInt_materielRuleid(int int_materielRuleid) {
		Int_materielRuleid = int_materielRuleid;
	}

    
	
	

}
