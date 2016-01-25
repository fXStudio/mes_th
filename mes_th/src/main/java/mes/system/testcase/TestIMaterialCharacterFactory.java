package mes.system.testcase;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;
import mes.system.elements.IMaterialCharacter;
import mes.system.factory.FactoryAdapter;
import mes.system.factory.IMaterialCharacterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestIMaterialCharacterFactory extends MyTestCase {

	private Connection con;

	private IMaterialCharacterFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = (IMaterialCharacterFactory) FactoryAdapter
				.getFactoryInstance(IMaterialCharacterFactory.class.getName());
		con = super.getConnection();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateElement() {
		IMaterialCharacter character = factory.createElement();
		Assert.assertNotNull(character);
	}

	@Test
	public void testQueryElementIntConnection() {
		try {
			IMaterialCharacter character = factory.queryElement(2, con);
			Assert.assertNull(character);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQueryElementStringConnection() {
		try {
			IMaterialCharacter character = factory.queryElement("测试物料特征1", con);
			Assert.assertNotNull(character);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		try {
			IMaterialCharacter character = factory.createElement();
			character.setName("测试物料特征3");
			character.setDescription("更新前描述");
			factory.save(character, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		try {
			IMaterialCharacter character = factory.queryElement("测试物料特征1", con);
			character.setDescription("更新后描述");
			factory.update(character, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
