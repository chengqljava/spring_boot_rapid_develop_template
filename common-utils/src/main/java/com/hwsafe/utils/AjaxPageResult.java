package com.hwsafe.utils;

/**
 * @author chengql 分页数据
 * @param <T>
 */
public class AjaxPageResult<T> extends AjaxResult<T> {

    /**  */
    private static final long serialVersionUID = 5218130065753193305L;
    /** 当前页码 */
    private int current;

    /** 总页数 */
    private long pages;

    /** 总共项数 */
    private long total;

    /** 每页项数 */
    private int size;

    public AjaxPageResult(boolean success, T value, int current, long pages,
            long total, int size) {
        super(success, value);
        this.current = current;
        this.pages = pages;
        this.total = total;
        this.size = size;
    }

    public AjaxPageResult() {
        super();
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
