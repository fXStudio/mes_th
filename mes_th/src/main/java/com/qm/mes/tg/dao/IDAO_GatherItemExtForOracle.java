/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherItemExt;

/**
 * 用于ORACLE的数据库操作
 *
 * @author YuanPeng
 */
public class IDAO_GatherItemExtForOracle implements IDAO_GatherItemExt {

    /**
     * 创建GatherItemExt
     * seq_tg_GatherItemExt.nextval为seq_tg_GatherItemExt序列下一值之意
     * @param gatherItemExt
     *                  采集点项属性对象
     * @return sql
     *          创建创建GatherItemExt的SQL语句
     */
    public String saveGatherItemExt(GatherItemExt gatherItemExt) {
        String sql = "insert into t_tg_Gatheritemext(INT_ID,INT_GATHER_ID,INT_ORDER,STR_NAME) "
				+ "values(seq_tg_GatherItemExt.nextval"+","
                + gatherItemExt.getGatherId()
                + ","
                + gatherItemExt.getOrder() 
                + ",'"
                + gatherItemExt.getName()
                +"')";
        return sql;
    }

    /**
     * 通过ID查询GatherItemExt
     *
     * @param id
     *          序号
     * @return sql
     *          通过ID查询GatherItemExt的SQL语句
     */
    public String getGatherItemExtById(int id) {
        String sql = "select INT_ID,INT_GATHER_ID,INT_ORDER,STR_NAME from t_tg_gatheritemext where INT_ID = "
                + id + "order by INT_ID";
        return sql;
    }

    /**
     * 查询最后一个采集点ID
     *
     * @return
     */
    public String getMaxGatherId(){
        String sql="select max(int_id) from T_TG_GATHER";
        return sql;
    }

    /**
     * 通过ID删除GatherItemExt
     *
     * @param id
     *          序号
     * @return sql
     *          通过ID删除GatherItemExt的SQL语句
     */
    public String delGatherItemExtById(int id) {
        String sql = "delete from t_tg_gatheritemext where INT_ID = " + id;
        return sql;
    }

    /**
     * 通过采集点ID删除对应的扩展属性
     *
     * @param gather_id
     * @return
     */
    public String delGatherItemExtByGatherId(int gather_id) {
        String sql = "delete from t_tg_gatheritemext where INT_GATHER_ID = " + gather_id;
        return sql;
    }

    /**
     * 更新GatherItemExt
     *
     * @param gatherItemExt
     *                  采集点项属性对象
     * @return sql
     *          更新GatherItemExt的SQL语句
     */
    public String updateGatherItemExt(GatherItemExt gatherItemExt) {
        String sql = "update t_tg_gatheritemext set INT_GATHER_ID = " + gatherItemExt.getGatherId()
                + ",INT_ORDER = " + gatherItemExt.getOrder()
                + ",STR_NAME = '" +gatherItemExt.getName()
                + "' where INT_ID = " + gatherItemExt.getId();
        return sql;
    }

    /**
     * 查询所有GatherItemExt
     *
     * @return sql
     *          查询所有GatherItemExt的SQL语句
     */
    public String getAllGatherItemExt() {
        String sql = "select INT_ID,INT_GATHER_ID,INT_ORDER,STR_NAME from t_tg_gatheritemext ";
        return sql;
    }

    /**
     * 通过name查询GatherItemExt
     *
     * @param name
     *          名字
     * @return sql
     *          通过name查询GatherItemExt的SQL语句
     */
    public String getGatherItemExtByName(String name) {
        String sql = "select INT_ID,INT_GATHER_ID,INT_ORDER,STR_NAME from t_tg_gatheritemext where STR_NAME = '"
                + name + "' order by STR_NAME";
        return sql;
    }

    /**
     * 通过GatherItemExt查询GatherItemExt
     *
     * @param int_gather_id
     * @return sql
     */
    public String getGatherItemExtByGatherId(int int_gather_id){
        String sql = "select INT_ID,INT_GATHER_ID,INT_ORDER,STR_NAME from t_tg_gatheritemext where INT_GATHER_ID = "
                + int_gather_id + " order by INT_ORDER";
        
        return sql;
    }
}
