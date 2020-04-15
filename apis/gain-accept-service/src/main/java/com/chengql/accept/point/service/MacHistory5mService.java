package com.hwsafe.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.accept.point.domain.MacHistory5m;
import com.hwsafe.accept.point.domain.query.MacHistory5mQuery;
import com.hwsafe.accept.point.mapper.MacHistory5mMapper;

/**
 * 探头监测历史5分钟数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Service
@Transactional(readOnly = true)
public class MacHistory5mService
        extends ServiceImpl<MacHistory5mMapper, MacHistory5m> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacHistory5m(MacHistory5m macHistory5m) {
        // TODO验证参数有效性
        return baseMapper.insert(macHistory5m) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacHistory5m> macHistory5ms) {
        // TODO验证参数有效性
        return this.saveOrUpdateBatch(macHistory5ms, macHistory5ms.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacHistory5m macHistory5m) {
        // TODO验证参数有效性
        return baseMapper.updateById(macHistory5m) > 0;
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
    public MacHistory5m selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacHistory5m> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macHistory5mQuery
     *            主键ID
     */
    public List<MacHistory5m> list(MacHistory5mQuery macHistory5mQuery) {
        // TODO验证参数有效性
        macHistory5mQuery.setCurrent(-1);
        return baseMapper.list(macHistory5mQuery);
    }

    public Page<MacHistory5m> listPage(MacHistory5mQuery macHistory5mQuery) {
        // TODO验证参数有效性
        macHistory5mQuery.setRecords(baseMapper.list(macHistory5mQuery));
        return macHistory5mQuery;
    }

}
