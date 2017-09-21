package com.tiza.process.protocol.handler.module;

import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import com.diyiliu.common.util.JacksonUtil;
import com.diyiliu.common.util.SpringUtil;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.dao.VehicleDao;
import com.tiza.process.common.model.Position;
import com.tiza.process.common.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Description: CurrentStatusModule
 * Author: DIYILIU
 * Update: 2017-09-14 15:14
 */
public class CurrentStatusModule  extends BaseHandle {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public RPTuple handle(RPTuple rpTuple) throws Exception {
        Map<String, String> context = rpTuple.getContext();

        String vehicleId = rpTuple.getTerminalID();
        if (context.containsKey(MStarConstant.FlowKey.POSITION) && context.containsKey(MStarConstant.FlowKey.STATUS)) {
            Position position = JacksonUtil.toObject(context.get(MStarConstant.FlowKey.POSITION), Position.class);
            Status status = JacksonUtil.toObject(context.get(MStarConstant.FlowKey.STATUS), Status.class);

            VehicleDao vehicleDao = SpringUtil.getBean("vehicleDao");
            Object[] values = new Object[]{position.getEnLngD(), position.getLatD(),
                    position.getSpeed(), position.getDirection(), position.getHeight(), position.getDateTime(),
                    status.getAcc(), status.getLocation(), status.getPowerOff(), status.getLowPower(),
                    status.getGpsFault(), status.getLoseAntenna(), status.getOnOff()};

            StringBuilder sqlBuilder = new StringBuilder("UPDATE bs_vehiclegpsinfo t SET ");
            sqlBuilder.append("t.encryptlng      = ?, ");
            sqlBuilder.append("t.encryptlat      = ?, ");
            sqlBuilder.append("t.speed           = ?, ");
            sqlBuilder.append("t.direction       = ?, ");
            sqlBuilder.append("t.altitude        = ?, ");
            sqlBuilder.append("t.gpstime         = ?, ");
            sqlBuilder.append("t.systemtime      = SYSDATE, ");
            sqlBuilder.append("t.accstatus       = ?, ");
            sqlBuilder.append("t.locationstatus  = ?, ");
            sqlBuilder.append("t.poweroff        = ?, ");
            sqlBuilder.append("t.lowvoltage      = ?, ");
            sqlBuilder.append("t.gpsmodulefault  = ?, ");
            sqlBuilder.append("t.gpsantennafault = ?, ");
            sqlBuilder.append("t.terminalstatus = ? "); // 开关机状态
            sqlBuilder.append(" WHERE t.vehicleid =").append(vehicleId);

            if (vehicleDao.update(sqlBuilder.toString(), values)) {
                //logger.info("车辆[{}]更新车辆当前表信息...", vehicleId);
            } else {
                logger.error("车辆[{}]更新车辆当前表信息失败!", vehicleId);
            }
        }

        return rpTuple;
    }

    @Override
    public void init() throws Exception {

    }

}
