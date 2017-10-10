package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description: CMD_03
 * Author: DIYILIU
 * Update: 2016-03-29 16:42
 */

@Service
public class CMD_03 extends M2DataProcess {

    public CMD_03() {
        this.cmd = 0x03;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        Map paramMap = (Map) argus[0];

        int interval = (int) paramMap.get("value");
        ByteBuf buf = Unpooled.buffer(2);
        buf.writeShort(interval);

        return headerToSendBytes(buf.array(), cmd, m2Header);
    }
}
