package com.tiza.process.m2.protocol.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.process.common.entity.Position;
import com.tiza.process.common.entity.Status;
import com.tiza.process.m2.bean.M2Header;
import com.tiza.process.m2.protocol.M2DataProcess;
import org.springframework.stereotype.Service;

/**
 * Description: CMD_85
 * Author: DIYILIU
 * Update: 2017-08-03 19:15
 */

@Service
public class CMD_85 extends M2DataProcess {

    public CMD_85() {
        this.cmd = 0x85;
    }

    @Override
    public void parse(byte[] content, Header header) {
        M2Header m2Header = (M2Header) header;

        Position position = renderPosition(content);
        Status status = renderStatus(position.getStatus());

        toKafka(m2Header, position, status);
    }
}
