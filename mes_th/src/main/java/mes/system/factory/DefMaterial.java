package mes.system.factory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import mes.system.elements.IMaterial;

class DefMaterial extends AElement implements IMaterial {

	private int materialTypeId = -1;

	private Set<Integer> characters = new HashSet<Integer>();
	
	private Set<Integer> identify = new HashSet<Integer>();

	public int getMaterialTypeId() {
		return materialTypeId;
	}

	public void setMaterialTypeId(int materialTypeId) {
		this.materialTypeId = materialTypeId;
	}

	public String toString() {
		return super.toString() + "\n\tDefMaterial¡ª¡ªmaterialtype:"
				+ materialTypeId+",characterids:"+characters+",identifyids:"+identify;
	}

	public void addCharacterId(int id) {
		characters.add(id);
	}

	public Iterator<Integer> getCharacterIds() {
		return characters.iterator();
	}
	
	public void addIdentifyId(int id){
		identify.add(id);
	}
	public Iterator<Integer> getIdentifyIds(){
		return identify.iterator();
	}

	public void removeCharacterId(int i) {
		characters.remove(i);
	}

	public void removeIdentifyId(int i) {
		identify.remove(i);
	}

}
