package com.tiza.process.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_84
 * Author: DIYILIU
 * Update: 2017-08-08 16:51
 */

@Service
public class CMD_84 extends M2DataProcess {

    public CMD_84() {
        this.cmd = 0x84;
    }

    @Override
    public void parse(byte[] content, Header header) {

    }
}
