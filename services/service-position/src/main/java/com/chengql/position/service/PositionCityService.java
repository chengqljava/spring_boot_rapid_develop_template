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
import com.hwsafe.position.domain.PositionCity;
import com.hwsafe.position.domain.query.PositionCityQuery;
import com.hwsafe.position.mapper.PositionCityMapper;
import com.hwsafe.validate.Check;

/**
 * 县级市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Service
@Transactional(readOnly = true)
public class PositionCityService
        extends ServiceImpl<PositionCityMapper, PositionCity> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean savePositionCity(PositionCity positionCity) {
        Check.checkNotNull(positionCity, ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return baseMapper.insert(positionCity) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<PositionCity> positionCitys) {
        Check.checkNotNull(positionCitys, ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return this.saveOrUpdateBatch(positionCitys, positionCitys.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(PositionCity positionCity) {
        Check.checkNotNull(positionCity, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.updateById(positionCity) > 0;
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
    public PositionCity getById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<PositionCity> listBatchIds(
            Collection<? extends Serializable> idList) {
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param positionCityQuery
     */
    public List<PositionCity> list(PositionCityQuery positionCityQuery) {
        Check.checkNotNull(positionCityQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionCityQuery.setSize(-1);// 查找所有 设置 size小于0
        return baseMapper.list(positionCityQuery);
    }

    /**
     * 根据 Query条件 查找分页列表
     *
     * @param positionCityQuery
     */
    public Page<PositionCity> listPage(PositionCityQuery positionCityQuery) {
        Check.checkNotNull(positionCityQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionCityQuery.setRecords(baseMapper.list(positionCityQuery));
        return positionCityQuery;
    }

    /**
     * @param provinceId
     * @return 通过省编码获得市列表
     */
    public List<PositionCity> selectByProvinceId(int provinceId) {
        QueryWrapper<PositionCity> queryWrapper = new QueryWrapper<PositionCity>();
        queryWrapper.eq("province_id", provinceId);
        return baseMapper.selectList(queryWrapper);
    }

}
