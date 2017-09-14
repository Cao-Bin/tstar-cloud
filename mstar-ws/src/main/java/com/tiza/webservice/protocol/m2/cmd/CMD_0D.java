package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.diyiliu.common.util.CommonUtil;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description: CMD_0D
 * Author: DIYILIU
 * Update: 2016-03-22 10:03
 */

@Service
public class CMD_0D extends M2DataProcess {

    public CMD_0D() {
        this.cmd = 0x0D;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        Map paramMap = (Map) argus[0];

        int paramId = (int) paramMap.get("value");
        byte[] bytes = CommonUtil.longToBytes(paramId, 2);

        return headerToSendBytes(bytes, cmd, m2Header);
    }
}
