package com.tiza.process.gb32960.protocol.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_06
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */

@Service
public class CMD_06 extends GB32960DataProcess{

    public CMD_06() {
        this.cmd = 0x06;
    }

    @Override
    public void parse(byte[] content, Header header) {



    }
}
