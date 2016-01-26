package com.qm.mes.pm.dao;


import com.qm.mes.pm.bean.ExceptionCause;
import com.qm.mes.pm.bean.ExceptionType;

/**
 * @author Xujia
 *
 */
public interface DAO_Exception {
	/**
	 * 创建异常类型的sql语句
	 * @param exType
	 * @return
	 */
	String saveExceptionType (ExceptionType exType);
	/**
	 * 通过序号查出异常类型的sql语句
	 * @param id
	 * @return
	 */
	String getExceptionTypeById(int id);
	/**
	 * 通过序号删除异常类型的sql语句
	 * @param id
	 * @return
	 */
	String delExceptionTypeById(int id);
	/**
	 * 查询出全部异常类型的sql语句
	 * @return
	 */
	String getAllExceptionType ();
	/**
	 * 通过名称查出异常类型的sql语句
	 * @param name
	 * @return
	 */
	String getExceptionTypeByName(String name);
	/**
	 * 创建异常原因的sql语句
	 * @param exCause
	 * @return
	 */
	String saveExceptionCause (ExceptionCause exCause);
	/**
	 * 通过序号查出异常原因的sql语句
	 * @param id
	 * @return
	 */
	String getExceptionCauseById(int id);
	/**
	 * 通过序号删除异常原因的sql语句
	 * @param id
	 * @return
	 */
	String delExceptionCauseById(int id);
	/**
	 * 查询出全部异常原因的sql语句
	 * @return
	 */
	String getAllExceptionCause();
	/**
	 * 更新ExceptionType对象，通过其id属性
	 *  徐嘉
	 * @param ExceptionType
	 * @return 更新ExceptionType的sql语句
	 */
	String updateExceptionType(ExceptionType exceptionType) ;
	/**
	 * 更新ExceptionCause对象，通过其id属性
	 *  徐嘉
	 * @param ExceptionCause
	 * @return 更新ExceptionCause的sql语句
	 */
	String updateExceptionCause(ExceptionCause exceptionCause) ;
	/**
	 * 检测ExceptionRecord表中是否有异常类型号
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkExceptionTypeById(int id);
	/**
	 * 检测ExceptionRecord表中是否有异常原因号
	 *  徐嘉
	 * @param id
	 * @return 关联个数
	 */
	String checkExceptionCauseById(int id);

}
