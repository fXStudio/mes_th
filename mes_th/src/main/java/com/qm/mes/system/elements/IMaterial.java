package com.qm.mes.system.elements;

import java.util.Iterator;

public interface IMaterial extends IElement {

	int getMaterialTypeId();

	void setMaterialTypeId(int materialTypeId);

	Iterator<Integer> getCharacterIds();

	void addCharacterId(int id);

	void addIdentifyId(int id);

	Iterator<Integer> getIdentifyIds();

	void removeCharacterId(int i);

	void removeIdentifyId(int i);

}
