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
import com.hwsafe.position.domain.PositionCounty;
import com.hwsafe.position.domain.query.PositionCountyQuery;
import com.hwsafe.position.mapper.PositionCountyMapper;
import com.hwsafe.validate.Check;

/**
 * 地区市数据库
 * 
 * @author chengql
 * @email chengql@hanwei.cn
 * @date 2019-09-10 11:31:05
 */
@Service
@Transactional(readOnly = true)
public class PositionCountyService
        extends ServiceImpl<PositionCountyMapper, PositionCounty> {

    /**
     * 插入一条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean savePositionCounty(PositionCounty positionCounty) {
        Check.checkNotNull(positionCounty, ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return baseMapper.insert(positionCounty) > 0;
    }

    /**
     * 插入批量条记录
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean saveBatch(List<PositionCounty> positionCountys) {
        Check.checkNotNull(positionCountys,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        // TODO主键 时间
        return this.saveOrUpdateBatch(positionCountys, positionCountys.size());
    }

    /**
     * 根据 ID 修改
     *
     * @param entity
     *            实体对象
     */
    @Transactional
    public boolean updateById(PositionCounty positionCounty) {
        Check.checkNotNull(positionCounty, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.updateById(positionCounty) > 0;
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
    public PositionCounty getById(Serializable id) {
        Check.checkNotBlank(id, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectById(id);
    }

    /**
     * 根据 ID 查找批量查找
     *
     * @param idList
     *            主键ID
     */
    public List<PositionCounty> listBatchIds(
            Collection<? extends Serializable> idList) {
        Check.checkNotNull(idList, ErrorTipTemplate.PARAMETER_NOT_NULL);
        return baseMapper.selectBatchIds(idList);
    }

    /**
     * 根据 Query条件 查找列表
     *
     * @param positionCountyQuery
     */
    public List<PositionCounty> list(PositionCountyQuery positionCountyQuery) {
        Check.checkNotNull(positionCountyQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionCountyQuery.setSize(-1);// 查找所有 设置 size小于0
        return baseMapper.list(positionCountyQuery);
    }

    /**
     * 根据 Query条件 查找分页列表
     *
     * @param positionCountyQuery
     */
    public Page<PositionCounty> listPage(
            PositionCountyQuery positionCountyQuery) {
        Check.checkNotNull(positionCountyQuery,
                ErrorTipTemplate.PARAMETER_NOT_NULL);
        positionCountyQuery.setRecords(baseMapper.list(positionCountyQuery));
        return positionCountyQuery;
    }

    /**
     * @param cityId
     * @return 通过城市ID获取
     */
    public List<PositionCounty> selectByCityId(Long cityId) {
        Check.checkNotNull(cityId, ErrorTipTemplate.PARAMETER_NOT_NULL);
        QueryWrapper<PositionCounty> queryWrapper = new QueryWrapper<PositionCounty>();
        queryWrapper.eq("city_id", cityId);
        return baseMapper.selectList(queryWrapper);
    }
}
