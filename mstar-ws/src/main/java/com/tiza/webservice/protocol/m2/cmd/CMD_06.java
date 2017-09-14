package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description: CMD_06
 * Author: DIYILIU
 * Update: 2016-04-11 14:37
 */

@Service
public class CMD_06 extends M2DataProcess {

    public CMD_06() {
        this.cmd = 0x06;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        Map paramMap = (Map) argus[0];

        int type = (int) paramMap.get("device");
        String url = (String) paramMap.get("url");
        int length = url.getBytes().length;

        byte[] content = new byte[length + 1];
        content[0] = (byte) type;

        System.arraycopy(url.getBytes(), 0, content, 1, length);

        return headerToSendBytes(content, cmd, m2Header);
    }
}
