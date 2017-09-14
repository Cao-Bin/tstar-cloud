package com.tiza.process.protocol.gb32960.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.protocol.gb32960.GB32960DataProcess;
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
