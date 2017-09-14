package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

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

        // 施维英0x03指令，无指令内容。

        return headerToSendBytes(new byte[0], cmd, m2Header);
    }
}
