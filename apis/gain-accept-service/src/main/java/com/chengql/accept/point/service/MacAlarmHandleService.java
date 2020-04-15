package com.hwsafe.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.accept.point.domain.MacAlarmHandle;
import com.hwsafe.accept.point.domain.query.MacAlarmHandleQuery;
import com.hwsafe.accept.point.mapper.MacAlarmHandleMapper;

/**
 * 监测报警信息处理表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Service
@Transactional(readOnly = true)
public class MacAlarmHandleService
        extends ServiceImpl<MacAlarmHandleMapper, MacAlarmHandle> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacAlarmHandle(MacAlarmHandle macAlarmHandle) {
        // TODO验证参数有效性
        return baseMapper.insert(macAlarmHandle) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacAlarmHandle> macAlarmHandles) {
        // TODO验证参数有效性
        return this.saveOrUpdateBatch(macAlarmHandles, macAlarmHandles.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacAlarmHandle macAlarmHandle) {
        // TODO验证参数有效性
        return baseMapper.updateById(macAlarmHandle) > 0;
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
    public MacAlarmHandle selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacAlarmHandle> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macAlarmHandleQuery
     *            主键ID
     */
    public List<MacAlarmHandle> list(MacAlarmHandleQuery macAlarmHandleQuery) {
        // TODO验证参数有效性
        macAlarmHandleQuery.setCurrent(-1);
        return baseMapper.list(macAlarmHandleQuery);
    }

    public Page<MacAlarmHandle> listPage(
            MacAlarmHandleQuery macAlarmHandleQuery) {
        // TODO验证参数有效性
        macAlarmHandleQuery.setRecords(baseMapper.list(macAlarmHandleQuery));
        return macAlarmHandleQuery;
    }

}
