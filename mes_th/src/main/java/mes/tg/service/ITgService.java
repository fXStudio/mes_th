package mes.tg.service;

import java.util.List;

import mes.tg.bean.MaterielRule;

public interface ITgService {

	List<String> savaTgRecord(List<String> in);

	boolean validate(List<String> in, List<MaterielRule> validates);

	void saveErrorMessage(String message);

	List<String> getErrorList();
}
