package com.tiza.process.common.support.task.impl;

import com.tiza.process.common.support.cache.ICache;
import com.tiza.process.common.support.dao.VehicleDao;
import com.tiza.process.common.support.entity.VehicleInfo;
import com.tiza.process.common.support.task.ITask;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description: RefreshVehicleInfoTask
 * Author: Wangw
 * Update: 2017-09-08 16:12
 */

public class RefreshVehicleInfoTask implements ITask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private VehicleDao vehicleDao;

    @Resource
    private ICache vehicleCacheProvider;

    @Override
    public void execute() {
        logger.info("refresh vehicle list...");

        List<VehicleInfo> list = vehicleDao.selectVehicleInfo();
        refresh(list, vehicleCacheProvider);
    }

    private void refresh(List<VehicleInfo> vehicleList, ICache vehicleCache) {
        if (vehicleList == null || vehicleList.size() < 1){
            logger.warn("no vehicles!");
            return;
        }

        Set oldKeys = vehicleCache.getKeys();
        Set tempKeys = new HashSet<>(vehicleList.size());

        for (VehicleInfo vehicle : vehicleList) {
            vehicleCache.put(vehicle.getVin(), vehicle);
            tempKeys.add(vehicle.getVin());
        }

        Collection subKeys = CollectionUtils.subtract(oldKeys, tempKeys);
        for (Iterator iterator = subKeys.iterator(); iterator.hasNext();){
            String key = (String) iterator.next();
            vehicleCache.remove(key);
        }
    }
}
