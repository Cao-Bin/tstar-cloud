package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.gb32960.bean.GB32960Header;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description: CMD_02
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */

@Service
public class CMD_02 extends GB32960DataProcess {

    public CMD_02() {
        this.cmd = 0x02;
    }

    private List<Map> paramValues = new ArrayList<>();;

    @Override
    public void parse(byte[] content, Header header) {
        ByteBuf buf = Unpooled.copiedBuffer(content);

        byte[] dateBytes = new byte[6];
        buf.readBytes(dateBytes);
        Date date = CommonUtil.bytesToDate(dateBytes);

        Map map = new HashMap();
        map.put("SYSTEMTIME", new Date());
        map.put("GPSTIME", date);
        paramValues.add(map);

        boolean error = false;
        while (buf.readableBytes() > 0) {
            int flag = buf.readByte();
            switch (flag) {

                case 0x01:

                    error = parseVehicle(buf);
                    break;
                case 0x02:

                    error = parseMotor(buf);
                    break;
                case 0x03:

                    error = parseBattery(buf);
                    break;
                case 0x04:

                    error = parseEngine(buf);
                    break;
                case 0x05:

                    error = parsePosition(buf);
                    break;
                case 0x06:

                    break;
                case 0x07:

                    break;
                default:
                    break;

            }
            if (error) {
                logger.error("parse byte[] error!");
                break;
            }
        }

        updateGpsInfo((GB32960Header) header, paramValues);
    }

    private boolean parseVehicle(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 20) {

            return true;
        }

        int vehStatus = byteBuf.readUnsignedByte();
        int charge = byteBuf.readUnsignedByte();
        int runMode = byteBuf.readUnsignedByte();

        int speed = byteBuf.readUnsignedShort();
        long mile = byteBuf.readUnsignedInt();

        int voltage = byteBuf.readUnsignedShort();
        int electricity = byteBuf.readUnsignedShort();

        int soc = byteBuf.readUnsignedByte();
        int dcStatus = byteBuf.readUnsignedByte();

        int gears = byteBuf.readUnsignedByte();
        int ohm = byteBuf.readUnsignedShort();
        byteBuf.readShort();


        Map map = new HashMap();
        map.put("VEHICLESTATUS", vehStatus);
        map.put("CHARGESTATUS", charge);
        map.put("DRIVINGMODE", runMode);

        // 速度
        if (0xFFFF == speed){

            map.put("SPEEDSTATUS", 255);
        }else if (0xFFFE == speed){

            map.put("SPEEDSTATUS", 254);
        }else {

            map.put("SPEEDSTATUS", 1);
            map.put("SPEED", speed);
        }

        // 里程
        if (0xFFFFFFFFl == mile){

            map.put("ODOSTATUS", 255);
        }else if (0xFFFFFFFEl == mile){

            map.put("ODOSTATUS", 254);
        }else {

            map.put("ODOSTATUS", 1);
            map.put("ODO", mile);
        }


        // 电压
        if (0xFFFF == voltage){

            map.put("VOLTAGESTATUS", 255);
        }else if (0xFFFE == voltage){

            map.put("VOLTAGESTATUS", 254);
        }else {

            map.put("VOLTAGESTATUS", 1);
            map.put("VOLTAGE", voltage);
        }

        // 电流
        if (0xFFFF == electricity){

            map.put("AMPSTATUS", 255);
        }else if (0xFFFE == electricity){

            map.put("AMPSTATUS", 254);
        }else {

            map.put("AMPSTATUS", 1);
            map.put("AMP", electricity);
        }

        // SOC
        if (0xFF == soc || 0xFE == soc){

            map.put("SOCSTATUS", soc);
        }else {

            map.put("SOCSTATUS", 1);
            map.put("SOC", soc);
        }

        // DC-DC
        map.put("DCDC", dcStatus);

        map.put("GEARS", gears);

        map.put("RESISTANCE", ohm);

        paramValues.add(map);

        return false;
    }

    private boolean parseMotor(ByteBuf byteBuf) {

        int count = byteBuf.readByte();
        if (byteBuf.readableBytes() < count * 12) {

            return true;
        }

        for (int i = 0; i < count; i++) {

            int serial = byteBuf.readByte();
            int status = byteBuf.readByte();

            int controlTemp = byteBuf.readByte();

            int rpm = byteBuf.readUnsignedShort();
            int torque = byteBuf.readUnsignedShort();

            int temp = byteBuf.readByte();
            int voltage = byteBuf.readShort();
            int electricity = byteBuf.readShort();
        }

        return false;
    }

    private boolean parseBattery(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() < 8) {

            return true;
        }
        int voltage = byteBuf.readShort();
        int electricity = byteBuf.readShort();

        int drain = byteBuf.readShort();

        int count = byteBuf.readShort();
        if (byteBuf.readableBytes() < count * 10) {

            return true;
        }

        for (int i = 0; i < count; i++) {

            int maxTemp = byteBuf.readShort();
            int tempNumber = byteBuf.readByte();

            int maxPPM = byteBuf.readShort();
            int ppmNumber = byteBuf.readByte();

            int maxPressure = byteBuf.readShort();
            int pressureNumber = byteBuf.readByte();

            int dcStatus = byteBuf.readByte();
        }

        return false;
    }

    private boolean parseEngine(ByteBuf byteBuf) {

        if (byteBuf.readableBytes() < 5){

            return true;
        }

        int status = byteBuf.readByte();
        int speed = byteBuf.readShort();
        int drain = byteBuf.readShort();

        return false;
    }

    private boolean parsePosition(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 9){

            return true;
        }

        int status = byteBuf.readByte();

        int effective = status & 0x01;
        int latDir = status & 0x02;
        int lngDir = status & 0x04;

        long lng = byteBuf.readUnsignedInt();
        long lat = byteBuf.readUnsignedInt();

        return false;
    }

    private boolean parseExtreme(ByteBuf byteBuf) {


        return false;
    }

    private boolean parseAlarm(ByteBuf byteBuf) {


        return false;
    }
}
