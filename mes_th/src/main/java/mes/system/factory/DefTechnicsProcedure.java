package mes.system.factory;

import mes.system.elements.ITechnicsProcedure;
import mes.system.elements.ITechnicsProcedureItem;

 class DefTechnicsProcedure extends AElement implements ITechnicsProcedure {
 
	private ITechnicsProcedureItem[] items;

	public ITechnicsProcedureItem[] getItems() {
		return items;
	}

	public void setItems(ITechnicsProcedureItem[] items) {
		this.items = items;
	}
	 
}
 
