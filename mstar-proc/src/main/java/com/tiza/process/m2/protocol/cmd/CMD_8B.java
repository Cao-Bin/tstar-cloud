package com.tiza.process.m2.protocol.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.m2.protocol.M2DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_8B
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_8B extends M2DataProcess {

    public CMD_8B() {
        this.cmd = 0x8B;
    }

    @Override
    public void parse(byte[] content, Header header) {
        super.parse(content, header);
    }
}
