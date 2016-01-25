package mes.tg.bean;

public class MaterielRule {
	private int id;// 序号
	private String name;// 验证名称
	private String validate;// 物料规则的验证字符串
	private String desc;// 物料规则验证字符串的描述信息

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
}
