package mes.system.testcase;

import java.sql.Connection;

import junit.framework.Assert;
import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.system.dao.IDAO_Material;
import mes.system.dao.IDAO_TechnicsProcedure;
import mes.system.elements.IMaterial;
import mes.system.factory.FactoryAdapter;
import mes.system.factory.IMaterialFactory;
import mes.system.factory.IMaterialTypeFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFactoryAdapter extends MyTestCase {

	@SuppressWarnings("unused")
	private Connection con;

	@Before
	public void setUp() throws Exception {
		con = super.getConnection();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDAOFactory() {
		Object obj = DAOFactoryAdapter.getInstance(DataBaseType.SQLSERVER,
				IDAO_Material.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(true, obj instanceof IDAO_Material);
		System.out.println((IDAO_Material) obj);

		obj = DAOFactoryAdapter.getInstance(DataBaseType.ORACLE,
				IDAO_Material.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(true, obj instanceof IDAO_Material);
		System.out.println(obj);

		obj = DAOFactoryAdapter.getInstance(DataBaseType.SQLSERVER,
				IDAO_TechnicsProcedure.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(true, obj instanceof IDAO_TechnicsProcedure);
		System.out.println(obj);
		obj = DAOFactoryAdapter.getInstance(DataBaseType.ORACLE,
				IDAO_TechnicsProcedure.class);
		Assert.assertNotNull(obj);
		Assert.assertEquals(true, obj instanceof IDAO_TechnicsProcedure);
		System.out.println(obj);
	}

	/**
	 * 测试加载工厂类
	 */
	@Test
	public void testGetFactoryInstance() {
		// 默认map是0
		Assert.assertEquals(FactoryAdapter.getFactoryMap().size(), 0);
		// 这个类是可以实例化出对象的
		Assert.assertNotNull(FactoryAdapter
				.getFactoryInstance(IMaterialFactory.class.getName()));
		// 加载后map内元素数为1
		Assert.assertEquals(FactoryAdapter.getFactoryMap().size(), 1);
		// 这个类是不能被实例化除对象的，应返回null值
		Assert.assertNull(FactoryAdapter.getFactoryInstance(IMaterial.class
				.getName()));
		// 由于加载失败所以map内元素数仍为1
		Assert.assertEquals(FactoryAdapter.getFactoryMap().size(), 1);
		// 这个类能加载并被实例化
		Assert.assertNotNull(FactoryAdapter
				.getFactoryInstance(IMaterialTypeFactory.class.getName()));
		// 加载成功，map内元素数加1
		Assert.assertEquals(FactoryAdapter.getFactoryMap().size(), 2);
		// 加载一个已经存在在map中的类型
		Assert.assertNotNull(FactoryAdapter
				.getFactoryInstance(IMaterialFactory.class.getName()));
		// 由于没有新的元素近来，map内元素数量不变
		Assert.assertEquals(FactoryAdapter.getFactoryMap().size(), 2);
	}

}
