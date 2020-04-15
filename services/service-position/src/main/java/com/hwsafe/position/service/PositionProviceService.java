package com.hwsafe.position.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.exception.ErrorTipTemplate;
import com.hwsafe.position.domain.PositionProvice;
import com.hwsafe.position.domain.query.PositionProviceQuery;
import com.hwsafe.position.mapper.PositionProviceMapper;
import com.hwsafe.validate.Check;

/**
 * 省份数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Service
@Transactional(readOnly = true)
public class PositionProviceService
        extends ServiceImpl<PositionProviceMapper, PositionProvice> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean savePositionProvice(PositionProvice positionProvice) {
        Check.checkNotNull(positionProvice,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return baseMapper.insert(positionProvice) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<PositionProvice> positionProvices) {
        Check.checkNotNull(positionProvices,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return this.saveOrUpdateBatch(positionProvices,
                positionProvices.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(PositionProvice positionProvice) {
        Check.checkNotNull(positionProvice,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.updateById(positionProvice) > 0;
    }

    /**
     * 根据 ID 删除
     *
     * @param id
     *            主键ID
     */
    @Transactional
    public boolean deleteById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
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
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.deleteBatchIds(idList) == idList.size();
    }

    /**
     * 根据 ID 查找
     *
     * @param id
     *            主键ID
     */
    public PositionProvice getById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<PositionProvice> listBatchIds(
            Collection<? extends Serializable> idList) {
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param positionProviceQuery
     */
    public List<PositionProvice> list(
            PositionProviceQuery positionProviceQuery) {
        Check.checkNotNull(positionProviceQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionProviceQuery.setSize(-1);// 查找所有 设置 size小于0
        return baseMapper.list(positionProviceQuery);
    }

    /**
     * 根据 Query条件 查找分页列表
     *
     * @param positionProviceQuery
     */
    public Page<PositionProvice> listPage(
            PositionProviceQuery positionProviceQuery) {
        Check.checkNotNull(positionProviceQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionProviceQuery.setRecords(baseMapper.list(positionProviceQuery));
        return positionProviceQuery;
    }

}
