package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description: CMD_09
 * Author: DIYILIU
 * Update: 2016-03-22 10:03
 */

@Service
public class CMD_09 extends M2DataProcess {

    public CMD_09() {
        this.cmd = 0x09;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        Map paramMap = (Map) argus[0];

        int accOn = (int) paramMap.get("accOn");
        int accOff = (int) paramMap.get("accOff");

        ByteBuf buf = Unpooled.buffer(4);
        buf.writeShort(accOn);
        buf.writeShort(accOff);

        return headerToSendBytes(buf.array(), cmd, m2Header);
    }
}
