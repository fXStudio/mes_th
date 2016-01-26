package com.qm.mes.system.factory;

import com.qm.mes.system.elements.ITechnicsProcedure;
import com.qm.mes.system.elements.ITechnicsProcedureItem;

 class DefTechnicsProcedure extends AElement implements ITechnicsProcedure {
 
	private ITechnicsProcedureItem[] items;

	public ITechnicsProcedureItem[] getItems() {
		return items;
	}

	public void setItems(ITechnicsProcedureItem[] items) {
		this.items = items;
	}
	 
}
 
