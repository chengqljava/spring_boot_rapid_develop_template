package com.chengql.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengql.accept.point.domain.MacHistory;
import com.chengql.accept.point.domain.query.MacHistoryQuery;
import com.chengql.accept.point.mapper.MacHistoryMapper;

/**
 * 监测历史表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Service
@Transactional(readOnly = true)
public class MacHistoryService
        extends ServiceImpl<MacHistoryMapper, MacHistory> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacHistory(MacHistory macHistory) {
        // TODO验证参数有效性
        return baseMapper.insert(macHistory) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacHistory> macHistorys) {
        // TODO验证参数有效性
        return this.saveBatch(macHistorys, macHistorys.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacHistory macHistory) {
        // TODO验证参数有效性
        return baseMapper.updateById(macHistory) > 0;
    }

    /**
     * 根据 ID 删除
     *
     * @param id
     *            主键ID
     */
    @Transactional
    public boolean deleteById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.deleteById(id) > 0;
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList
     *            主键ID列表(不能为 null 以及 empty)
     */
    @Transactional
    public boolean deleteByIds(Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.deleteBatchIds(idList) == idList.size();
    }

    /**
     * 根据 ID 查找
     *
     * @param id
     *            主键ID
     */
    public MacHistory selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacHistory> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macHistoryQuery
     *            主键ID
     */
    public List<MacHistory> list(MacHistoryQuery macHistoryQuery) {
        // TODO验证参数有效性
        macHistoryQuery.setCurrent(-1);
        return baseMapper.list(macHistoryQuery);
    }

    public Page<MacHistory> listPage(MacHistoryQuery macHistoryQuery) {
        // TODO验证参数有效性
        macHistoryQuery.setRecords(baseMapper.list(macHistoryQuery));
        return macHistoryQuery;
    }

}
