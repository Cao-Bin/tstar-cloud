package com.tiza.process.gb32960.protocol;

import com.tiza.process.common.support.cache.ICache;
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

    @Override
    public Header dealHeader(byte[] bytes) {

        ByteBuf buf = Unpooled.copiedBuffer(bytes);

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

    @Override
    public void init() {
        cmdCacheProvider.put(cmd, this);
    }

    private static GB32960ParseHandler handler;
    public static void setHandler(GB32960ParseHandler handler) {
        GB32960DataProcess.handler = handler;
    }
}
