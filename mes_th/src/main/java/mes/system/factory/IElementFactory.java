package mes.system.factory;

import java.sql.Connection;
import java.sql.SQLException;

import mes.system.elements.IElement;


public interface IElementFactory<element extends IElement> {

	public abstract element createElement();

	public abstract boolean update(element e, Connection con)
			throws SQLException;

	/**
	 * 存储元素<br>
	 * 当处理复杂元素的时候，可能出现同时对多个表进行调整的情况。<br>
	 * 所以此方法会在需要的时候起动数据库的事务。
	 * 
	 * @param e
	 *            目标元素
	 * @param con
	 *            数据库连接
	 * @return 
	 * @throws SQLException
	 */
	public abstract boolean save(element e, Connection con) throws SQLException;

	/**
	 * 删除目标的物料类型——将已弃用标志置“1”
	 * 
	 * @param id
	 *            物料类型id
	 * @param con
	 *            目标数据库连接
	 * @return 返回操作是否成功，true为成功
	 * @throws SQLException
	 */
	public abstract boolean deleteElement(int id, Connection con)
			throws SQLException;

	/**
	 * 删除目标的物料类型——将已弃用标志置“1”
	 * 
	 * @param name
	 *            物料名称
	 * @param con
	 *            目标数据库连接
	 * @return 返回操作是否成功，true为成功
	 * @throws SQLException
	 */
	public abstract boolean deleteElement(String name, Connection con)
			throws SQLException;

	public abstract boolean deleteElement(element e, Connection con)
			throws SQLException;

	public abstract element queryElement(int id, Connection con)
			throws SQLException;

	public abstract element queryElement(String name, Connection con)
			throws SQLException;

}