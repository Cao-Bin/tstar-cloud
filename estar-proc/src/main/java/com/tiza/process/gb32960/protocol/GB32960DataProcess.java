package com.tiza.process.gb32960.protocol;

import com.tiza.process.common.support.cache.ICache;
import com.tiza.process.common.support.dao.VehicleDao;
import com.tiza.process.common.support.entity.VehicleInfo;
import com.tiza.process.common.support.model.Header;
import com.tiza.process.common.support.model.IDataProcess;
import com.tiza.process.gb32960.bean.GB32960Header;
import com.tiza.process.gb32960.handler.GB32960ParseHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description: GB32960DataProcess
 * Author: Wangw
 * Update: 2017-09-06 16:57
 */

@Service
public class GB32960DataProcess implements IDataProcess {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected int cmd = 0xFF;

    @Resource
    protected ICache cmdCacheProvider;

    @Resource
    protected ICache vehicleCacheProvider;

    @Resource
    private VehicleDao vehicleDao;

    @Override
    public Header dealHeader(byte[] bytes) {

        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        // 读取头标志[0x23,0x23]
        buf.readBytes(new byte[2]);

        int cmd = buf.readByte();
        int resp = buf.readByte();

        byte[] vinBytes = new byte[17];
        buf.readBytes(vinBytes);
        String vin = new String(vinBytes);

        // 加密方式
        buf.readByte();

        int length = buf.readUnsignedShort();
        byte[] content = new byte[length];
        buf.readBytes(content);

        GB32960Header header = new GB32960Header();
        header.setCmd(cmd);
        header.setResp(resp);
        header.setVin(vin);
        header.setContent(content);

        return header;
    }

    @Override
    public void parse(byte[] content, Header header) {

    }

    /**
     * 更新车辆当前位置表
     *
     * @param header
     * @param paramValues
     *
     */
    protected void updateGpsInfo(GB32960Header header, List<Map> paramValues){

        String vin = header.getVin();
        if (!vehicleCacheProvider.containsKey(vin)){
            logger.warn("[{}] is not in the list of vehicles", vin);
            return;
        }
        VehicleInfo vehicleInfo = (VehicleInfo) vehicleCacheProvider.get(vin);

        List list = new ArrayList();
        StringBuilder strb = new StringBuilder("update BS_VEHICLEGPSINFO set ");
        for (Map map: paramValues){

            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();){

                String key = (String) iterator.next();
                Object value = map.get(key);

                strb.append(key).append("=?, ");
                list.add(value);
            }
        }

        String sql = strb.substring(0, strb.length() - 2) + " where VEHICLEID=" + vehicleInfo.getId();
        vehicleDao.update(sql, list.toArray());
    }

    @Override
    public void init() {
        cmdCacheProvider.put(cmd, this);
    }

    private static GB32960ParseHandler handler;
    public static void setHandler(GB32960ParseHandler handler) {
        GB32960DataProcess.handler = handler;
    }
}
