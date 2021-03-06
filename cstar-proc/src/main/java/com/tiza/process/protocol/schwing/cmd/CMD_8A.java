package com.tiza.process.protocol.schwing.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.protocol.schwing.SchwingDataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_8A
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_8A extends SchwingDataProcess {

    public CMD_8A() {
        this.cmd = 0x8A;
    }

    @Override
    public void parse(byte[] content, Header header) {
        super.parse(content, header);
    }
}
