package com.tiza.process.common.task;

import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.task.ITask;
import com.tiza.process.common.dao.VehicleDao;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description: RefreshVehicleStorehouseTask
 * Author: DIYILIU
 * Update: 2017-09-19 09:53
 */
public class RefreshVehicleStorehouseTask implements ITask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ICache vehicleStorehouseCacheProvider;

    @Resource
    private VehicleDao vehicleDao;

    @Override
    public void execute() {
        logger.info("刷新车辆仓库信息...");

        Map map = vehicleDao.selectVehicleStorehouse();
        refresh(map, vehicleStorehouseCacheProvider);
    }

    public void refresh(Map map, ICache provider){

        Set oldKeys = provider.getKeys();
        Set tempKeys = new HashSet(map.size());

        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();){

            int id = (int) iterator.next();
            List list = (List) map.get(id);
            provider.put(id, list);
        }

        // 被删除的
        Collection subKeys = CollectionUtils.subtract(oldKeys, tempKeys);
        for (Iterator iterator = subKeys.iterator(); iterator.hasNext();){
            int key = (int) iterator.next();
            provider.remove(key);
        }
    }
}
