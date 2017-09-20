package com.tiza.process.protocol.handler.module;

import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.model.Point;
import com.diyiliu.common.util.JacksonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.dao.VehicleDao;
import com.tiza.process.common.model.InOutRecord;
import com.tiza.process.common.model.Position;
import com.tiza.process.common.model.Status;
import com.tiza.process.common.model.Storehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description: StrategyAlarmModule
 * Author: DIYILIU
 * Update: 2017-09-19 09:00
 */
public class StrategyAlarmModule extends BaseHandle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ICache recordMap;
    private ICache vehicleStorehouseMap;

    @Override
    public RPTuple handle(RPTuple rpTuple) throws Exception {
        recordMap = SpringUtil.getBean("vehicleOutInCacheProvider");
        vehicleStorehouseMap = SpringUtil.getBean("vehicleStorehouseCacheProvider");


        Map<String, String> context = rpTuple.getContext();

        String vehicleId = rpTuple.getTerminalID();
        if (context.containsKey(MStarConstant.FlowKey.POSITION) && context.containsKey(MStarConstant.FlowKey.STATUS)) {
            Position position = JacksonUtil.toObject(context.get(MStarConstant.FlowKey.POSITION), Position.class);
            Status status = JacksonUtil.toObject(context.get(MStarConstant.FlowKey.STATUS), Status.class);

            // 有效定位 并且 车辆有仓库
            if (status.getLocation() == 1 && vehicleStorehouseMap.containsKey(vehicleId)) {

                int vehId = Integer.parseInt(vehicleId);

                // 当前位置
                Point point = new Point(position.getLng(), position.getLat());
                List<Storehouse> storehouseList = (List<Storehouse>) vehicleStorehouseMap.get(vehicleId);

                Storehouse storehouse = null;
                for (Storehouse sh : storehouseList) {
                    if (sh.getArea().isPointInArea(point) == 1) {

                        storehouse = sh;
                        break;
                    }
                }

                if (recordMap.containsKey(vehId)) {
                    InOutRecord oldRecord = (InOutRecord) recordMap.get(vehicleId);

                    if (storehouse == null){

                        // 出库
                        if (oldRecord.getStatus() == 1){

                            toUpdate(position, vehId, oldRecord.getStorehouseId(), 0);
                        }
                    }else{

                        // 入库
                        if (storehouse.getId() != oldRecord.getStorehouseId() || oldRecord.getStatus() == 0){

                            toUpdate(position, vehId, storehouse.getId(), 1);
                        }
                    }
                } else {

                    // 新增入库信息
                    if (storehouse != null){

                        toUpdate(position, vehId, storehouse.getId(),1);
                    }
                }
            }
        }

        return rpTuple;
    }

    @Override
    public void init() throws Exception {

    }

    private void toUpdate(Position position, int vehicleId, int storehouseId, int status) {
        String sql = "INSERT INTO bs_warehouseoutin" +
                "(vehicleid, unitid, gpstime, encryptlng, encryptlat, systemtime, status) " +
                "VALUES(?,?,?,?,?,?,?)";

        Object[] paramValues = new Object[]{vehicleId, storehouseId,
                position.getDateTime(), position.getEnLngD(), position.getEnLatD(),
                new Date(), status};

        VehicleDao vehicleDao = SpringUtil.getBean("vehicleDao");
        if (vehicleDao.update(sql, paramValues)){
            //logger.info("新增车辆策略报警成功...");
        }else {
            logger.error("新增车辆策略报警失败！");
        }

        InOutRecord record = new InOutRecord();
        record.setStatus(status);
        record.setStorehouseId(storehouseId);

        recordMap.put(vehicleId, record);
    }
}
