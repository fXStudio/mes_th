package com.qm.mes.system.factory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class FactoryAdapter {
	private static Map<String, AElementFactory> map = new HashMap<String, AElementFactory>();

	private FactoryAdapter() {
	}

	public static Map<String, AElementFactory> getFactoryMap() {
		return new HashMap<String, AElementFactory>(map);
	}

	public static IElementFactory getFactoryInstance(String classname) {
		AElementFactory result = null;
		result = map.get(classname);
		if (result != null)
			return result;
		try {
			Object object = Class.forName(classname).newInstance();
			if (object instanceof AElementFactory)
				result = (AElementFactory) object;
			map.put(classname, result);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
}
