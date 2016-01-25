// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Service_CheckAndCreatePowerString.java

package mes.framework.services.role;

import java.util.Comparator;

// Referenced classes of package mes.framework.services.role:
//			Service_CheckAndCreatePowerString

@SuppressWarnings("unchecked")
class Service_CheckAndCreatePowerString$11 implements Comparator {

	public int compare(String o1, String o2) {
		try {
			int v1;
			int v2;
			v1 = Integer.valueOf(o1).intValue();
			v2 = Integer.valueOf(o2).intValue();
			if (v1 == v2)
				return 0;
			return v1 <= v2 ? -1 : 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public int compare(Object obj, Object obj1) {
		return compare((String) obj, (String) obj1);
	}

	Service_CheckAndCreatePowerString$11() {
		super();
	}
}
