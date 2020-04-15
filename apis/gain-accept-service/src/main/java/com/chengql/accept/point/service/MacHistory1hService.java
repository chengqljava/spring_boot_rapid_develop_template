package com.hwsafe.accept.point.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.accept.point.domain.MacHistory1h;
import com.hwsafe.accept.point.domain.query.MacHistory1hQuery;
import com.hwsafe.accept.point.mapper.MacHistory1hMapper;

/**
 * 探头监测历史1小时数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-12-23 17:05:45
 */
@Service
@Transactional(readOnly = true)
public class MacHistory1hService
        extends ServiceImpl<MacHistory1hMapper, MacHistory1h> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveMacHistory1h(MacHistory1h macHistory1h) {
        // TODO验证参数有效性
        return baseMapper.insert(macHistory1h) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<MacHistory1h> macHistory1hs) {
        // TODO验证参数有效性
        return this.saveOrUpdateBatch(macHistory1hs, macHistory1hs.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(MacHistory1h macHistory1h) {
        // TODO验证参数有效性
        return baseMapper.updateById(macHistory1h) > 0;
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
    public MacHistory1h selectById(Serializable id) {
        // TODO验证参数有效性
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<MacHistory1h> selectBatchIds(
            Collection<? extends Serializable> idList) {
        // TODO验证参数有效性
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param macHistory1hQuery
     *            主键ID
     */
    public List<MacHistory1h> list(MacHistory1hQuery macHistory1hQuery) {
        // TODO验证参数有效性
        macHistory1hQuery.setCurrent(-1);
        return baseMapper.list(macHistory1hQuery);
    }

    public Page<MacHistory1h> listPage(MacHistory1hQuery macHistory1hQuery) {
        // TODO验证参数有效性
        macHistory1hQuery.setRecords(baseMapper.list(macHistory1hQuery));
        return macHistory1hQuery;
    }

}
