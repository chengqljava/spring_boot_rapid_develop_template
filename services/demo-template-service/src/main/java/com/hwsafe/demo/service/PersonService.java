package com.hwsafe.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwsafe.demo.domain.Person;
import com.hwsafe.demo.domain.PersonQuery;
import com.hwsafe.demo.mapper.PersonMapper;
import com.hwsafe.utils.IDGenerator;
import com.hwsafe.validate.Check;

/**
 * @author chengql service 进行字段判断
 */
@Service
public class PersonService extends ServiceImpl<PersonMapper, Person> {

    /**
     * @param page
     * @param name
     * @return 有参
     */
    public Page<Person> selectPersonPage(Page<Person> page, String name) {
        Check.checkNotNull(name, "姓名不能为空");
        page.setRecords(baseMapper.selectListByName(page, name));
        return page;
    }

    public Page<Person> selectPersonPageWrapper(Page<Person> page,
            String name) {
        Check.checkNotNull(name, "姓名不能为空");
        QueryWrapper<Person> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        IPage<Person> ipage = baseMapper.selectPage(page, queryWrapper);
        return (Page) ipage;
    }

    /**
     * @param name
     * @param age
     * @return 更新
     */
    @Transactional
    public Person save(String name, Integer age) {
        Check.checkNotNull(name, "姓名不能为空");
        Check.checkNotNull(age, "年龄不能为空");
        Check.checkArgument(age > 0, "年龄必须大于0");
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setId(IDGenerator.OBJECTID.generate());
        super.save(person);
        return person;
    }

    /**
     * @param name
     * @param age
     * @param id
     * @return 更新
     */
    @Transactional
    public Person update(String name, Integer age, String id) {
        Check.checkNotNull(name, "姓名不能为空");
        Check.checkNotNull(age, "年龄不能为空");
        Check.checkArgument(age > 0, "年龄必须大于0");
        Check.checkNotNull(id, "主键不能为空");
        Person personOld = baseMapper.selectById(id);
        Check.checkNotNull(personOld, "数据不存在");

        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setId(id);
        super.updateById(person);
        return person;
    }

    /**
     * @param page
     * @return 无参分页
     */
    public Page<Person> selectPersonPage(PersonQuery personQuery) {
        Check.checkNotNull(personQuery.getName(), "姓名不能为空");
        Page<Person> page = new Page<Person>(personQuery.getCurrent(),
                personQuery.getCurrent());
        page.setRecords(
                baseMapper.selectListByName(page, personQuery.getName()));
        return page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baomidou.mybatisplus.extension.service.IService#list() 无参LIST
     */
    public List<Person> list() {

        return baseMapper.list();
    }
}
