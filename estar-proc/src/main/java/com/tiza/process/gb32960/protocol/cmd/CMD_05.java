package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;

/**
 * Description: CMD_05
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */
public class CMD_05 extends GB32960DataProcess{

    public CMD_05() {
        this.cmd = 0x05;
    }

    @Override
    public void parse(byte[] content, Header header) {



    }
}
