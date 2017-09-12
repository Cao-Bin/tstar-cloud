package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_04
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */

@Service
public class CMD_04 extends GB32960DataProcess{

    public CMD_04() {
        this.cmd = 0x04;
    }

    @Override
    public void parse(byte[] content, Header header) {



    }
}
