package com.tiza.process.protocol.m2;

import cn.com.tiza.tstar.common.process.BaseHandle;
import cn.com.tiza.tstar.common.process.RPTuple;
import com.diyiliu.common.cache.ICache;
import com.diyiliu.common.model.Header;
import com.diyiliu.common.model.IDataProcess;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.DateUtil;
import com.diyiliu.common.util.JacksonUtil;
import com.tiza.process.common.config.MStarConstant;
import com.tiza.process.common.model.Parameter;
import com.tiza.process.common.model.Position;
import com.tiza.process.common.model.Status;
import com.tiza.process.common.model.VehicleInfo;
import com.tiza.process.protocol.bean.M2Header;
import com.tiza.process.protocol.handler.M2ParseHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: M2DataProcess
 * Author: DIYILIU
 * Update: 2017-09-14 14:25
 */
public class M2DataProcess implements IDataProcess{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected int cmd = 0xFF;

    protected static BaseHandle handler;

    @Resource
    protected ICache cmdCacheProvider;
    @Resource
    protected ICache vehicleCacheProvider;

    @Override
    public Header dealHeader(byte[] bytes) {

        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        int length = buf.readUnsignedShort();

        byte[] termi = new byte[5];
        buf.readBytes(termi);
        String terminalId = CommonUtil.parseSIM(termi);

        int version = buf.readByte();

        int factory = buf.readByte();

        int terminalType = buf.readByte();

        int user = buf.readByte();

        int serial = buf.readUnsignedShort();

        int cmd = buf.readUnsignedByte();

        byte[] content = new byte[buf.readableBytes() - 3];
        buf.readBytes(content);

        int check = buf.readByte();

        byte[] end = new byte[2];
        buf.readBytes(end);

        return new M2Header(cmd, length, terminalId,
                version, factory, terminalType,
                user, serial, content,
                check, end);
    }

    @Override
    public void parse(byte[] content, Header header) {

    }

    @Override
    public byte[] pack(Header header, Object... argus) {

        return null;
    }

    public void init() {
        cmdCacheProvider.put(cmd, this);
    }


    /**
     * 位置、工况信息
     * @param header
     * @param position
     * @param status
     * @param parameter
     */
    public void toKafka(M2Header header, Position position, Status status, Parameter parameter) {
        String terminalId = header.getTerminalId();
        if (!vehicleCacheProvider.containsKey(terminalId)) {
            logger.warn("该终端[{}]不存在车辆列表中...", terminalId);
            return;
        }

        VehicleInfo vehicle = (VehicleInfo) vehicleCacheProvider.get(terminalId);

        Map posMap = new HashMap();
        posMap.put(MStarConstant.Location.GPS_TIME,
                DateUtil.dateToString(position.getDateTime()));
        posMap.put(MStarConstant.Location.SPEED, position.getSpeed());
        posMap.put(MStarConstant.Location.ALTITUDE, position.getHeight());
        posMap.put(MStarConstant.Location.DIRECTION, position.getDirection());
        posMap.put(MStarConstant.Location.LOCATION_STATUS, status.getLocation());
        posMap.put(MStarConstant.Location.ACC_STATUS, status.getAcc());
        posMap.put(MStarConstant.Location.ORIGINAL_LNG, position.getLngD());
        posMap.put(MStarConstant.Location.ORIGINAL_LAT, position.getLatD());
        posMap.put(MStarConstant.Location.LNG, position.getEnLngD());
        posMap.put(MStarConstant.Location.LAT, position.getEnLatD());

        posMap.put(MStarConstant.Location.VEHICLE_ID, vehicle.getId());

        RPTuple rpTuple = new RPTuple();
        rpTuple.setCmdID(header.getCmd());
        rpTuple.setCmdSerialNo(header.getSerial());

        rpTuple.setTerminalID(String.valueOf(vehicle.getId()));

        String msgBody = JacksonUtil.toJson(posMap);
        rpTuple.setMsgBody(msgBody.getBytes(Charset.forName(MStarConstant.JSON_CHARSET)));
        rpTuple.setTime(position.getDateTime().getTime());

        // 将解析的位置和状态信息放入流中
        RPTuple tuple = (RPTuple) header.gettStarData();
        // 修改ID(原来是SIM)为车辆ID
        tuple.setTerminalID(String.valueOf(vehicle.getId()));

        Map<String, String> context = tuple.getContext();
        context.put(MStarConstant.FlowKey.POSITION, JacksonUtil.toJson(position));
        context.put(MStarConstant.FlowKey.STATUS, JacksonUtil.toJson(status));
        //context.put(Constant.FlowKey.PARAMETER, JacksonUtil.toJson(parameter));

        logger.info("终端[{}]写入Kafka位置信息...", terminalId);
        handler.storeInKafka(rpTuple, context.get(MStarConstant.Kafka.TRACK_TOPIC));
    }

    /**
     * 开关机信息(统计累计工作时间)
     * @param header
     * @param dateList
     */
    public void toKafka(M2Header header, List<Date> dateList){
        String terminalId = header.getTerminalId();
        if (!vehicleCacheProvider.containsKey(terminalId)) {
            logger.warn("该终端[{}]不存在车辆列表中...", terminalId);
            return;
        }

        VehicleInfo vehicle = (VehicleInfo) vehicleCacheProvider.get(terminalId);

        // 获取上下文终中的配置信息
        RPTuple tuple = (RPTuple) header.gettStarData();
        Map<String, String> context = tuple.getContext();

        Map wtMap = new HashMap();
        for (int i = 0; i < dateList.size(); i += 2){

            Date starTime = dateList.get(i);
            Date endTime = dateList.get(i + 1);

            wtMap.put(MStarConstant.WorkTime.START_TIME, DateUtil.dateToString(starTime));
            wtMap.put(MStarConstant.WorkTime.END_TIME, DateUtil.dateToString(endTime));
            wtMap.put(MStarConstant.WorkTime.VEHICLE_ID, vehicle.getId());

            RPTuple rpTuple = new RPTuple();
            rpTuple.setCmdID(header.getCmd());
            rpTuple.setCmdSerialNo(header.getSerial());
            rpTuple.setTerminalID(String.valueOf(vehicle.getId()));

            String msgBody = JacksonUtil.toJson(wtMap);
            rpTuple.setMsgBody(msgBody.getBytes(Charset.forName(MStarConstant.JSON_CHARSET)));
            rpTuple.setTime(starTime.getTime());

            logger.info("终端[{}]写入Kafka开关机信息(累计工作时间)...", terminalId);
            handler.storeInKafka(rpTuple, context.get(MStarConstant.Kafka.WORK_TIME_TOPIC));
        }
    }


    protected Position renderPosition(byte[] bytes) {
        if (bytes.length < 16) {
            logger.error("长度不足，无法获取位置信息！");
            return null;
        }

        ByteBuf buf = Unpooled.copiedBuffer(bytes);
        long lat = buf.readUnsignedInt();
        long lng = buf.readUnsignedInt();
        int speed = buf.readUnsignedByte();
        int direction = buf.readUnsignedByte();
        byte[] heightBytes = new byte[2];
        buf.readBytes(heightBytes);
        int height = CommonUtil.renderHeight(heightBytes);
        byte[] statusBytes = new byte[4];
        buf.readBytes(statusBytes);
        long status = CommonUtil.bytesToLong(statusBytes);

        Date dateTime = new Date();
        if (bytes.length > 16) {
            byte[] dateBytes = null;
            if (bytes.length == 19) {
                dateBytes = new byte[3];
            } else if (bytes.length == 22) {
                dateBytes = new byte[6];
            }
            buf.readBytes(dateBytes);
            dateTime = CommonUtil.bytesToDate(dateBytes);
        }

        return new Position(lng, lat, speed, direction, height, status, dateTime);
    }

    protected Status renderStatus(long l) {
        Status status = new Status();

        status.setLocation(statusBit(l, 0));
        status.setLat(statusBit(l, 1));
        status.setLng(statusBit(l, 2));
        status.setAcc(statusBit(l, 3));
        status.setLock(statusBit(l, 4));
        status.setDiscontinue(statusBit(l, 8));
        status.setPowerOff(statusBit(l, 9));
        status.setLowPower(statusBit(l, 10));
        status.setChangeSIM(statusBit(l, 11));
        status.setGpsFault(statusBit(l, 12));
        status.setLoseAntenna(statusBit(l, 13));
        status.setAerialCircuit(statusBit(l, 14));
        status.setPowerDefence(statusBit(l, 15));
        status.setOverSpeed(statusBit(l, 16));
        status.setTrailer(statusBit(l, 17));
        status.setUncap(statusBit(l, 18));

        return status;
    }

    private int statusBit(long l, int offset) {

        return new Long((l >> offset) & 0x01).intValue();
    }

    public static void setHandle(BaseHandle parseHandler) {
        handler = parseHandler;
    }
}
