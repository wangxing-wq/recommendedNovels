package com.novels.common.web;

import java.util.List;

/**
 * @author 22343
 * @version 1.0
 * <br/>抽象类BaseDao
 */
public interface BaseDao<K, T> {

    /**
     * 插入值
     * @param bean 插入Bean对象值
     * @return 是否插入成功
     */
    boolean insert(T bean);

    /**
     * 批量插入
     * @param beans 批量插入值
     * @return 是否插入成功
     */
    boolean batchInsert(List<T> beans);

    /**
     * 通过主键列进行删除
     * @param key 主键
     * @return 是否删除成功
     */
    boolean deleteById(K key);

    /**
     * 通过主键列进行批量删除
     * @param keyList 主键
     * @return 是否删除成功
     */
    boolean deleteByIdList(List<K> keyList);


    /**
     * 通过主键 Bean对象
     * @param bean Bean对象值
     * @return 是否修改成功
     */
    boolean updateById(T bean);

    /**
     * 通过主键,进行可选择修改
     * @param bean 修改对象
     * @return 返回是否修改成功
     */
    boolean updateSelectiveById(T bean);

    /**
     * 通过Id 查找元素
     * @param k 主键
     * @return T  返回元素
     */
    T findById(K k);

    /**
     * 通过非空可选择条件删除
     * @param condition 条件
     * @return 查询元素
     */
    List<T> findByBeanSelective(T condition);

}
