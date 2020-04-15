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
import com.hwsafe.position.domain.PositionVillage;
import com.hwsafe.position.domain.query.PositionVillageQuery;
import com.hwsafe.position.mapper.PositionVillageMapper;
import com.hwsafe.validate.Check;

/**
 * 省市县镇村数据
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Service
@Transactional(readOnly = true)
public class PositionVillageService
        extends ServiceImpl<PositionVillageMapper, PositionVillage> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean savePositionVillage(PositionVillage positionVillage) {
        Check.checkNotNull(positionVillage,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return baseMapper.insert(positionVillage) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<PositionVillage> positionVillages) {
        Check.checkNotNull(positionVillages,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return this.saveOrUpdateBatch(positionVillages,
                positionVillages.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(PositionVillage positionVillage) {
        Check.checkNotNull(positionVillage,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.updateById(positionVillage) > 0;
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
    public PositionVillage getById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<PositionVillage> listBatchIds(
            Collection<? extends Serializable> idList) {
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param positionVillageQuery
     */
    public List<PositionVillage> list(
            PositionVillageQuery positionVillageQuery) {
        Check.checkNotNull(positionVillageQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionVillageQuery.setSize(-1);// 查找所有 设置 size小于0
        return baseMapper.list(positionVillageQuery);
    }

    /**
     * 根据 Query条件 查找分页列表
     *
     * @param positionVillageQuery
     */
    public Page<PositionVillage> listPage(
            PositionVillageQuery positionVillageQuery) {
        Check.checkNotNull(positionVillageQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionVillageQuery.setRecords(baseMapper.list(positionVillageQuery));
        return positionVillageQuery;
    }

    /**
     * @param countyId
     * @return 通过街道 镇获取
     */
    public List<PositionVillage> selectByTownId(Long townId) {
        Check.checkNotNull(townId, ErrorTipTemplate.PARAMETER_NOT_NULL);
        QueryWrapper<PositionVillage> queryWrapper = new QueryWrapper<PositionVillage>();
        queryWrapper.eq("town_id", townId);
        return baseMapper.selectList(queryWrapper);
    }

}
