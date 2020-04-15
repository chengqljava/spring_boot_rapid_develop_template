package com.hwsafe.position.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.exception.ErrorTipTemplate;
import com.hwsafe.position.domain.PositionTown;
import com.hwsafe.position.domain.query.PositionTownQuery;
import com.hwsafe.position.mapper.PositionTownMapper;
import com.hwsafe.validate.Check;

/**
 * 镇数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Service
@Transactional(readOnly = true)
public class PositionTownService
        extends ServiceImpl<PositionTownMapper, PositionTown> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean savePositionTown(PositionTown positionTown) {
        Check.checkNotNull(positionTown, ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return baseMapper.insert(positionTown) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<PositionTown> positionTowns) {
        Check.checkNotNull(positionTowns, ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return this.saveOrUpdateBatch(positionTowns, positionTowns.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(PositionTown positionTown) {
        Check.checkNotNull(positionTown, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.updateById(positionTown) > 0;
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
    public PositionTown getById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<PositionTown> listBatchIds(
            Collection<? extends Serializable> idList) {
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param positionTownQuery
     */
    public List<PositionTown> list(PositionTownQuery positionTownQuery) {
        Check.checkNotNull(positionTownQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionTownQuery.setSize(-1);// 查找所有 设置 size小于0
        return baseMapper.list(positionTownQuery);
    }

    /**
     * 根据 Query条件 查找分页列表
     *
     * @param positionTownQuery
     */
    public Page<PositionTown> listPage(PositionTownQuery positionTownQuery) {
        Check.checkNotNull(positionTownQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionTownQuery.setRecords(baseMapper.list(positionTownQuery));
        return positionTownQuery;
    }

    /**
     * @param countyId
     * @return 通过县、区ID获取
     */
    public List<PositionTown> selectByCountyId(Long countyId) {
        Check.checkNotNull(countyId, ErrorTipTemplate.PARAMETER_NOT_NULL);
        QueryWrapper<PositionTown> queryWrapper = new QueryWrapper<PositionTown>();
        queryWrapper.eq("county_id", countyId);
        return baseMapper.selectList(queryWrapper);
    }

}
