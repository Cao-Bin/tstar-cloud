package com.tiza.process.m2.protocol.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.common.entity.Position;
import com.tiza.process.common.entity.Status;
import com.tiza.process.m2.bean.M2Header;
import com.tiza.process.m2.protocol.M2DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_89
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_89 extends M2DataProcess {

    public CMD_89() {
        this.cmd = 0x89;
    }

    @Override
    public void parse(byte[] content, Header header) {
        M2Header m2Header = (M2Header) header;

        Position position = renderPosition(content);
        Status status = renderStatus(position.getStatus());
    }
}
