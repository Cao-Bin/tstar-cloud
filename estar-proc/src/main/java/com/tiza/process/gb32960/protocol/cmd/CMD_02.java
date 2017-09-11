package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * Description: CMD_02
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */
public class CMD_02 extends GB32960DataProcess {

    public CMD_02() {
        this.cmd = 0x02;
    }

    @Override
    public void parse(byte[] content, Header header) {
        ByteBuf buf = Unpooled.copiedBuffer(content);

        byte[] dateBytes = new byte[6];
        buf.readBytes(dateBytes);
        Date date = CommonUtil.bytesToDate(dateBytes);

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
    }

    private boolean parseVehicle(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 20) {

            return true;
        }

        int vehStatus = byteBuf.readByte();
        int charge = byteBuf.readByte();
        int runMode = byteBuf.readByte();

        int speed = byteBuf.readShort();
        int mile = byteBuf.readInt();

        int voltage = byteBuf.readShort();
        int electricity = byteBuf.readShort();

        int soc = byteBuf.readByte();
        int dcStatus = byteBuf.readByte();

        int gears = byteBuf.readByte();
        int ohm = byteBuf.readShort();
        byteBuf.readShort();

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
