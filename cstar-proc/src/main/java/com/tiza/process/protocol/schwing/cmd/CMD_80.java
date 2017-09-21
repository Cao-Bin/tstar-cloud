package com.tiza.process.protocol.schwing.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.protocol.schwing.SchwingDataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_80
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_80 extends SchwingDataProcess {

    public CMD_80() {
        this.cmd = 0x80;
    }

    @Override
    public void parse(byte[] content, Header header) {


    }
}
