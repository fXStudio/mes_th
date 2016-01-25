package mes.system.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mes.framework.DataBaseType;
import mes.system.dao.DAOFactoryAdapter;
import mes.system.dao.IDAO_Element;
import mes.system.elements.IElement;

/**
 * 元素工厂<br>
 * ，基本上所有的元素都是从IElement接口的子类型，<br>
 * 所以在它们实现功能的时候也大多涉及到对IElement的操作，<br>
 * 这里将这些操作集中并写成抽象类供其他子类型使用，达到重用的效果。
 * 
 * @author 张光磊 2008-3-6
 */
abstract class AElementFactory<element extends IElement> implements
		IElementFactory<element> {

	public boolean deleteElement(element e, Connection con) throws SQLException {
		return deleteElement(e.getName(), con);
	}

	public boolean deleteElement(int id, Connection con) throws SQLException {
		// 获得物料类型的DAO对象
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		if (doUpdateStatement(dao.getSQL_deleteElement(id), con) == 0)
			return false;// 目标元素不存在。
		return true;
	}

	public boolean deleteElement(String name, Connection con)
			throws SQLException {
		// 获得物料类型的DAO对象
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		if (doUpdateStatement(dao.getSQL_deleteElement(name), con) == 0)
			return false;// 目标元素不存在。
		return true;
	}

	public element queryElement(int id, Connection con) throws SQLException {
		return null;
	}

	public element queryElement(String name, Connection con)
			throws SQLException {
		element element = createElement();
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		String sql = dao.getSQL_queryElement(name);
		Statement st = con.createStatement();
		ResultSet set = st.executeQuery(sql);
		if (set.next()) {
			element.setId(set.getInt("id"));
			element.setName(set.getString("name"));
			element.setDiscard(set.getInt("discard") == 1 ? true : false);
			element.setDescription(set.getString("description"));
			element.setUpdateDateTime(set.getDate("updateDateTime"));
			element.setUpdateUserId(set.getInt("updateUserId"));
			element.setVersion(set.getInt("version"));
		}
		st.close();
		return element;
	}

	public final boolean save(element obj, Connection con) throws SQLException {
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		return doTrans(obj, dao.getSQL_innerElement(obj), con,true);
	}

	/**
	 * 这个方法是在启动<b><font color="ff0000">事务</font></b>后调用的用于存储元素相关信息的方法。
	 * <p>
	 * <b><font color="ff0000">所以一定要注意释放资源，切记。</font></b>
	 * @param e
	 *            要存储的元素
	 * @param con
	 *            数据库连接
	 * @return 返回方法运行状态
	 * @throws SQLException
	 *@deprecated <font color="ff0000">此方法不建议外部调用，请使用save方法。</font> 
	 */
	abstract boolean saveElement(element e, Connection con)
			throws SQLException;

	/**
	 * @deprecated <font color="ff0000">此方法不建议外部调用，请使用update方法。</font>
	 */
	abstract boolean updateElement(element e, Connection con)
			throws SQLException;

	protected void initObjectFromResultSet(element element, ResultSet set)
			throws SQLException {
		element.setId(set.getInt("id"));
		element.setName(set.getString("name"));
		element.setDescription(set.getString("description"));
		element.setDiscard(set.getInt("discard") == 1 ? true : false);
		element.setUpdateDateTime(set.getDate("updatedatetime"));
		element.setUpdateUserId(set.getInt("updateuserid"));
		element.setVersion(set.getInt("version"));
	}

	protected int doUpdateStatement(String sql, Connection con)
			throws SQLException {
		Statement st = con.createStatement();
		int i = st.executeUpdate(sql);
		st.close();
		return i;
	}

	public final boolean update(element obj, Connection con)
			throws SQLException {
		IDAO_Element dao = (IDAO_Element) DAOFactoryAdapter.getInstance(
				DataBaseType.getDataBaseType(con), IDAO_Element.class);
		return doTrans(obj, dao.getSQL_updateElement(obj), con,false);
	}

	@SuppressWarnings("deprecation")
	private synchronized boolean doTrans(element obj, String sql, Connection con,
			boolean isSave) throws SQLException {
		boolean isReleaseLock = false;
		try {
			if (con.getAutoCommit()) { // 若当前连接没有启动事务机制，则手动启动
				isReleaseLock = true;
				con.setAutoCommit(false);
			}// 以下是对表的操作
			doUpdateStatement(sql, con);
			if (isSave)
				saveElement(obj, con);
			else
				updateElement(obj, con);
			if (isReleaseLock)// 若事务是手头开启的
				con.commit();// 提交事务
			return true;
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			if (isReleaseLock)
				try {
					con.setAutoCommit(true);
				} catch (Exception e) {
				}
		}
	}
}
