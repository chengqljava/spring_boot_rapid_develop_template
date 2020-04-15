package com.hwsafe.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.accept.point.domain.MacAlarmFault;
import com.hwsafe.accept.point.domain.query.MacAlarmFaultQuery;
import com.hwsafe.accept.point.mapper.MacAlarmFaultMapper;

/**
 * 故障报警表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Service
@Transactional(readOnly = true)
public class MacAlarmFaultService
        extends ServiceImpl<MacAlarmFaultMapper, MacAlarmFault> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacAlarmFault(MacAlarmFault macAlarmFault) {
        // TODO验证参数有效性
        return baseMapper.insert(macAlarmFault) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacAlarmFault> macAlarmFaults) {
        // TODO验证参数有效性
        return this.saveOrUpdateBatch(macAlarmFaults, macAlarmFaults.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacAlarmFault macAlarmFault) {
        // TODO验证参数有效性
        return baseMapper.updateById(macAlarmFault) > 0;
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
    public MacAlarmFault selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacAlarmFault> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macAlarmFaultQuery
     *            主键ID
     */
    public List<MacAlarmFault> list(MacAlarmFaultQuery macAlarmFaultQuery) {
        // TODO验证参数有效性
        macAlarmFaultQuery.setCurrent(-1);
        return baseMapper.list(macAlarmFaultQuery);
    }

    public Page<MacAlarmFault> listPage(MacAlarmFaultQuery macAlarmFaultQuery) {
        // TODO验证参数有效性
        macAlarmFaultQuery.setRecords(baseMapper.list(macAlarmFaultQuery));
        return macAlarmFaultQuery;
    }

}
