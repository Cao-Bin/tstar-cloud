package com.tiza.gateway.gb32960;

import cn.com.tiza.tstar.common.entity.TStarData;
import cn.com.tiza.tstar.gateway.handler.BaseUserDefinedHandler;
import com.tiza.gateway.common.util.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Description: GB32960ProtocolHandler
 * Author: Wangw
 * Update: 2017-09-05 11:41
 */

public class GB32960ProtocolHandler extends BaseUserDefinedHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public TStarData handleRecvMessage(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {

        byte[] bytes = new byte[byteBuf.readableBytes() - 2];
        byteBuf.getBytes(2, bytes);

        byte last = bytes[bytes.length - 1];
        byte check = CommonUtil.getCheck(Arrays.copyOf(bytes, bytes.length - 1));
        if (last != check){

            logger.error("check code error!");
            return null;
        }

        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        int cmd = buf.readByte();
        int resp = buf.readByte();

        byte[] vinArray = new byte[17];
        buf.readBytes(vinArray);

        String vin = new String(vinArray);

        TStarData tStarData = new TStarData();
        tStarData.setMsgBody(bytes);
        tStarData.setCmdID(cmd);
        tStarData.setTerminalID(vin);
        tStarData.setTime(System.currentTimeMillis());

        // 需要应答
        if (resp == 0xFE){


        }

        logger.info("收到消息，终端[{}]指令[{}], 内容[{}]...", vin, String.format("%02X", cmd), CommonUtil.bytesToStr(bytes));

        return tStarData;
    }


}
