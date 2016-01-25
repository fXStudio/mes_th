package mes.system.dao;

import mes.system.elements.IElement;

public class IDAO_ElementForOracle extends DAO_Element {

	public String getSQL_innerElement(String name, String description,
			int updateUserId) {
		// 元素表需要一个自增序列。
		return "insert into Element(id,	name,description,discard,updateDateTime,updateUserId,version"
				+ ")values(seq_element.NEXTVAL,'"
				+ name
				+ "','"
				+ description
				+ "',0,sysdate," + updateUserId + ",1)";

	}

	public String getSQL_innerElement(IElement element) {
		return getSQL_innerElement(element.getName(), element.getDescription(),
				element.getUpdateUserId());
	}

}
