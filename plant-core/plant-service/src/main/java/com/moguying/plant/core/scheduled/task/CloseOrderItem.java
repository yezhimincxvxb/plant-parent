package com.moguying.plant.core.scheduled.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class CloseOrderItem implements Runnable {

    // 对应订单id
    protected Integer id;
    // 对应环数(若环数为0的订单则关单)
    protected int loop;

}
