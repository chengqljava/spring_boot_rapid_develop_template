package com.hwsafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author chengql SAX 读取
 *
 * @param <T>
 */
public class ExcelReadListener<T> extends AnalysisEventListener<T> {

    List<T> dataList = new ArrayList<>();

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data
     *            one row value. Is is same as
     *            {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        dataList.add(data);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    public List<T> getDataList() {
        return dataList;
    }

}
