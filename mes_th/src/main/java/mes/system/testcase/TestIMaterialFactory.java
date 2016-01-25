package mes.system.testcase;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;
import mes.system.elements.IMaterial;
import mes.system.factory.FactoryAdapter;
import mes.system.factory.IMaterialFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestIMaterialFactory extends MyTestCase {

	private Connection con; 

	private IMaterialFactory mfactory;

	@Before
	public void setUp() throws Exception {
		mfactory = (IMaterialFactory) FactoryAdapter
				.getFactoryInstance(IMaterialFactory.class.getName());
		con = super.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		con.close();
	}

	@Test
	public void testCreateElement() {
		IMaterial material = mfactory.createElement();
		
		Assert.assertNotNull(material);
	}

	@Test
	public void testSaveIMaterialConnection() {
		IMaterial material = mfactory.createElement();
		material.setName("测试物料3");
		material.setMaterialTypeId(-1);
		material.addCharacterId(40);
		material.addCharacterId(42);
		material.addCharacterId(45);
		material.addIdentifyId(30);
		material.addIdentifyId(31);
		material.addIdentifyId(32);
//		try {
//			mfactory.save(material, con);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void testUpdateElementIMaterialConnection() {
		try {
			IMaterial material = this.mfactory.queryElement("测试物料2", con);
			System.out.println("befor update description..");
			System.out.println(material);
			material.setDescription("测试物料2：测试描述。");
			material.setMaterialTypeId(-2);
			material.removeCharacterId(40);
			material.removeIdentifyId(31);
			mfactory.update(material, con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryElementIntConnection() {
		try {
			IMaterial material = this.mfactory.queryElement("测试物料2", con);
			System.out.println("queryElement:");
			System.out.println(material);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryElementStringConnection() {
//		fail("Not yet implemented"); 
	}

}
