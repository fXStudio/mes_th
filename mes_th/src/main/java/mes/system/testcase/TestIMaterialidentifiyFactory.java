package mes.system.testcase;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;
import mes.system.elements.IMaterialidentify;
import mes.system.factory.FactoryAdapter;
import mes.system.factory.IMaterialidentifyFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestIMaterialidentifiyFactory extends MyTestCase {

	private Connection con;

	private IMaterialidentifyFactory factory;

	@Before
	public void setUp() throws Exception {
		con = this.getConnection();
		factory = (IMaterialidentifyFactory) FactoryAdapter
				.getFactoryInstance(IMaterialidentifyFactory.class.getName());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateElement() {
		IMaterialidentify materialidentify = factory.createElement();
		Assert.assertNotNull(materialidentify);
	}

	@Test
	public void testDeleteElementElementConnection() {
		IMaterialidentify materialidentify;
		try {
			materialidentify = factory.queryElement("测试标识：VIN2", con);
			Assert.assertNotNull(materialidentify);
			factory.deleteElement(materialidentify, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		IMaterialidentify materialidentify = factory.createElement();
		Assert.assertNotNull(materialidentify);
		materialidentify.setName("测试标识：VIN3");
		try {
			factory.save(materialidentify, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateElement() {
		IMaterialidentify materialidentify;
		try {
			materialidentify = factory.queryElement("测试标识：VIN", con);
			Assert.assertNotNull(materialidentify);
			materialidentify = factory.queryElement("测试标识：VIN2", con);
			Assert.assertNotNull(materialidentify);
			materialidentify = factory.queryElement("测试标识：VIN3", con);
			Assert.assertNotNull(materialidentify);
			materialidentify.setCodeLength(10);
			factory.update(materialidentify, con);
			System.out.println(materialidentify);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
