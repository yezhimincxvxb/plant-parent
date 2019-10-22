package com.moguying.plant.core.scheduled.task;

public abstract class CloseOrderItem implements Runnable{


    public CloseOrderItem(Integer id, int loop) {
        this.id = id;
        this.loop = loop;
    }

    //对应id
    protected Integer id;

    //对应环数
    protected int loop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    @Override
    public String toString() {
        return "CloseOrderItem{" +
                "id=" + id +
                ", loop=" + loop +
                '}';
    }
}
