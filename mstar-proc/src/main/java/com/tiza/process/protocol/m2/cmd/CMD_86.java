package com.tiza.process.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_86
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_86 extends M2DataProcess {

    public CMD_86() {
        this.cmd = 0x86;
    }

    @Override
    public void parse(byte[] content, Header header) {



    }
}
