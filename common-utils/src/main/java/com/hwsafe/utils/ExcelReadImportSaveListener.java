package com.hwsafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

/**
 * @author chengql SAX 读取中进行保存数据库
 *
 * @param <T>
 */
public class ExcelReadImportSaveListener<T> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    List<T> dataList = new ArrayList<>();
    List<T> dataErrorList = new ArrayList<>();

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
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (dataList.size() >= BATCH_COUNT) {
            // 保存数据到数据库saveData();
            // 存储完成清理 list
            dataList.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        // 保存数据，确保最后遗留的数据也存储到数据库
        // saveData();
        // importDataBatchSave();
    }

    public List<T> getDataErrorList() {
        return dataErrorList;
    }
}
