package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: CMD_01
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */
public class CMD_01 extends GB32960DataProcess{

    public CMD_01() {
        this.cmd = 0x01;
    }

    @Override
    public void parse(byte[] content, Header header) {
        ByteBuf buf = Unpooled.copiedBuffer(content);

        byte[] dateBytes = new byte[6];
        buf.readBytes(dateBytes);
        Date date = CommonUtil.bytesToDate(dateBytes);

        int serial = buf.readUnsignedShort();

        byte[] iccidBytes = new byte[20];
        buf.readBytes(iccidBytes);
        String iccid = new String(iccidBytes);

        int count = buf.readByte();
        int length = buf.readByte();
        if (buf.readableBytes() < count * length){

            logger.warn("解析可充电储能系统字节长度不足!");
            return;
        }

        List<String> codes = new ArrayList<>();
        for (int i = 0; i < count; i++){

            byte[] codeBytes = new byte[length];
            buf.readBytes(codeBytes);
            String code = new String(codeBytes);

            codes.add(code);
        }
    }
}
