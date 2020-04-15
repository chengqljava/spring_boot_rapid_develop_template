package com.hwsafe.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.accept.point.domain.MacProbe;
import com.hwsafe.accept.point.domain.query.MacProbeQuery;
import com.hwsafe.accept.point.mapper.MacProbeMapper;

/**
 * 监测探头信息表
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-24 09:41:01
 */
@Service
@Transactional(readOnly = true)
public class MacProbeService extends ServiceImpl<MacProbeMapper, MacProbe> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacProbe(MacProbe macProbe) {
        // TODO验证参数有效性
        return baseMapper.insert(macProbe) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacProbe> macProbes) {
        // TODO验证参数有效性
        return this.saveOrUpdateBatch(macProbes, macProbes.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacProbe macProbe) {
        // TODO验证参数有效性
        return baseMapper.updateById(macProbe) > 0;
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
    public MacProbe selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacProbe> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macProbeQuery
     *            主键ID
     */
    public List<MacProbe> list(MacProbeQuery macProbeQuery) {
        // TODO验证参数有效性
        macProbeQuery.setCurrent(-1);
        return baseMapper.list(macProbeQuery);
    }

    public Page<MacProbe> listPage(MacProbeQuery macProbeQuery) {
        // TODO验证参数有效性
        macProbeQuery.setRecords(baseMapper.list(macProbeQuery));
        return macProbeQuery;
    }

}
