package com.hwsafe.ottService.base.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.hwsafe.ottService.base.utils.Context;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

@Configuration
@MapperScan("com.hwsafe.*.mapper")
public class MybatisPlusConfig {
    /**
     * 租户的统一字段名称
     */
    @Value("${tenant.id.column:}")
    private String tenantIdColumn;
    /**
     * 租户忽略的表 表名以，分开
     */
    @Value("${tenant.ignore.table:}")
    private String tenantIgnoreTable;
    /**
     * 租户忽略的 SELECT UPDATE DELETE INSERT SQL关键词
     */
    @Value("${tenant.ignore.sql:}")
    private String tenantIgnoreSql;
    /**
     * 租户忽略的某一个方法 例如 com.hwsafe.common.tenant.mapper.TenantMapper.list
     */
    @Value("${tenant.ignore.method:}")
    private String tenantIgnoreMethod;
    @Value("${tenant.switch.flag:}")
    private Boolean tenantSwitchFlag;

    /**
     * 分页插件
     * 
     * 
     * 多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br> 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】
         * 这条记录（ 注意观察 SQL ）<br>
         */
        // 租户开关
        if (tenantSwitchFlag != null && tenantSwitchFlag) {
            List<ISqlParser> sqlParserList = new ArrayList<>();
            TenantSqlParser tenantSqlParser = new OttTenantParser();
            tenantSqlParser.setTenantHandler(new TenantHandler() {
                /**
                 * tenant_id in (1,2)
                 * 
                 * @return
                 */
                public Expression getTenantId(boolean where) {
                    final boolean multipleTenantIds = true;
                    if (where && multipleTenantIds) {
                        return multipleTenantIdCondition();
                    } else {
                        return singleTenantIdCondition();
                    }
                }

                private Expression singleTenantIdCondition() {
                    return new StringValue(
                            Context.getWebStartIngredients().getTenantId());// ID自己想办法获取到
                }

                private Expression multipleTenantIdCondition() {
                    final InExpression inExpression = new InExpression();
                    inExpression
                            .setLeftExpression(new Column(getTenantIdColumn()));
                    final ExpressionList itemsList = new ExpressionList();
                    List<Expression> inValues = new ArrayList<>();

                    // if (!Context.getUser().getTenantIdList().isEmpty()) {
                    // for (String tenantId :
                    // Context.getUser().getTenantIdList()) {
                    inValues.add(new StringValue(
                            Context.getWebStartIngredients().getTenantId()));
                    // }
                    // }
                    itemsList.setExpressions(inValues);
                    inExpression.setRightItemsList(itemsList);
                    return inExpression;
                }

                @Override
                public String getTenantIdColumn() {
                    return tenantIdColumn;
                }

                @Override
                public boolean doTableFilter(String tableName) {
                    // 这里可以判断是否过滤表
                    /*
                     * if ("user".equals(tableName)) { return true; }
                     */
//                    return false;
                    if (StringUtils.isNoneBlank(tenantIgnoreTable)) {
                        for (String table : tenantIgnoreTable.split(",")) {
                            if (table.equalsIgnoreCase(tableName)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }

            });

            sqlParserList.add(tenantSqlParser);
            paginationInterceptor.setSqlParserList(sqlParserList);

            paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {

                @Override
                public boolean doFilter(MetaObject metaObject) {
                    // TODO Auto-generated method stub

                    MappedStatement ms = SqlParserHelper
                            .getMappedStatement(metaObject);
                    // 过滤自定义查询此时无租户信息约束出现
                    // "com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId
                    // ()) System.out.println(ms.getId());
                    // metaObject.get
                    // ms.gets
                    // 1判断 SQL
                    if (StringUtils.isNoneBlank(tenantIgnoreSql)) {
                        for (String sqlStr : tenantIgnoreSql.split(",")) {
                            if (sqlStr.equalsIgnoreCase(
                                    ms.getSqlCommandType().name())) {
                                return true;
                            }
                        }
                    }

                    // 2 判断方法
                    if (StringUtils.isNoneBlank(tenantIgnoreMethod)) {
                        for (String method : tenantIgnoreMethod.split(",")) {
                            if (method.equals(ms.getId())) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
        return paginationInterceptor;
    }

}
