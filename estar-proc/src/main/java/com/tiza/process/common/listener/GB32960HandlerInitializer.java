package com.tiza.process.common.listener;

import com.diyiliu.common.listener.Initializer;
import com.diyiliu.common.task.ITask;

import javax.annotation.Resource;

/**
 * Description: GB32960HandlerInitializer
 * Author: DIYILIU
 * Update: 2017-09-21 15:48
 */
public class GB32960HandlerInitializer implements Initializer {

    @Resource
    private ITask refreshVehicleInfoTask;

    @Override
    public void init() {

        // 刷新车辆列表
        refreshVehicleInfoTask.execute();
    }

}
