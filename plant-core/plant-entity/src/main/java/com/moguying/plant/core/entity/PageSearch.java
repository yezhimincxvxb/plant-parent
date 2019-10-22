package com.moguying.plant.core.entity;

public class PageSearch<T> {
    private Integer page = 1;
    private Integer size = 10;
    private T where;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public T getWhere() {
        return where;
    }

    public void setWhere(T where) {
        this.where = where;
    }
}
