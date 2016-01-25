package mes.framework;

/**
 * 服务项工厂<br>
 * 
 * @author 张光磊 2007-6-21
 */
public final class ServiceItemFactory {

	/**
	 * 通过给出服务项的名字和描述获得一个服务项接口的实例
	 * 
	 * @param name
	 *            名字
	 * @param descr
	 *            描述
	 * @return 新建立的实例
	 */
	public static IServiceItem createParameterItem(String name, String descr) {
		return new DefServiceItem(name, descr);
	}

}
