/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherItemExt;

/**
 *用于SQLSERVER的数据库操作
 *
 * @author YuanPeng
 */
public class IDAO_GatherItemExtForSqlserver extends IDAO_GatherItemExtForOracle{

    /**
     * 创建saveGatherItemExt
     *
     * @param gatherItemExt
     *                  采集点项属性对象
     * @return sql
     *          创建gatherItemExt的sql语句
     */
    public String saveGatherItemExt(GatherItemExt gatherItemExt) {
        String sql = "insert into t_tg_Gatheritemext(INT_GATHER_ID,INT_ORDER,STR_NAME) "
				+ "values("
                + gatherItemExt.getGatherId()
                + ","
                + gatherItemExt.getOrder()
                + ","
                + gatherItemExt.getName()
                +")";
        return sql;
    }

}
