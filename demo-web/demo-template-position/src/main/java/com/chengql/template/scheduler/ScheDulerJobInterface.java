package com.hwsafe.template.scheduler;

/**
 * @author chengql 定时任务接口
 *
 */
public interface ScheDulerJobInterface {
    /**
     * @param 执行任务
     */
    public void jobTask();

    /**
     * @param params
     *            带参数任务
     */
    public void paramsJobTask(String params);
}
