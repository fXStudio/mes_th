/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qm.mes.tg.dao;

import com.qm.mes.tg.bean.GatherItemExt;
/**
 * 持久层接口类
 *
 * @author YuanPeng
 */
public interface IDAO_GatherItemExt {

    /**
     * 创建GatherItemExt对象
     *
     * @param gatherItemExt
     *                      实体对象
     *
     * @return
     */
    String saveGatherItemExt(GatherItemExt gatherItemExt);

    /**
     * 通过序号查出GatherItemExt对象
     *
     * @param id
     *           序号（唯一）
     * @return int_id
     *                  序号<br>
     *          int_gatherId
     *                  采集点ID<br>
     *          int_order
     *                  采集点顺序号<br>
     *          str_name
     *                  扩展属性名<br>
     */
    String getGatherItemExtById(int id);

    /**
     * 查询最后一个采集点ID
     *
     * @param id
     *           序号（唯一）
     * @return int_id
     *                  序号<br>
     *          int_gatherId
     *                  采集点ID<br>
     *          int_order
     *                  采集点顺序号<br>
     *          str_name
     *                  扩展属性名<br>
     */
    String getMaxGatherId();

    /**
     * 通过序号删除GatherItemExt对象
     *
     * @param id
     *          序号（唯一）
     * @return  
     */
    String delGatherItemExtById(int id);

    /**
     * 通过采集点ID删除对应的扩展属性
     * 
     * @param gather_id
     *              采集点ID
     * @return
     */
    String delGatherItemExtByGatherId(int gather_id);

    /**
     * 修改GatherItemExt对象
     *
     * @param gatherItemExt
     *                  采集点扩展属性对象
     * @return 
     */
    String updateGatherItemExt (GatherItemExt gatherItemExt);

    /**
     * 查询出全部GatherItemExt对象
     *
     * @return List<GatherItemExt>
     *                  采集点扩展属性序列
     */
    String getAllGatherItemExt ();

    /**
     *通过名字查出GatherItemExt对象
     *
     * @param name
     *           名字
     * @return List<GatherItemExt>
     *                  采集点扩展属性序列
     */
    String getGatherItemExtByName(String name);

    /**
     * 通过采集点序号查出GatherItemExt对象
     * 
     * @param int_gather_id
     * @return int_id
     *                  序号<br>
     *          int_gatherId
     *                  采集点ID<br>
     *          int_order
     *                  采集点顺序号<br>
     *          str_name
     *                  扩展属性名<br>
     */
    String getGatherItemExtByGatherId(int int_gather_id);
}
