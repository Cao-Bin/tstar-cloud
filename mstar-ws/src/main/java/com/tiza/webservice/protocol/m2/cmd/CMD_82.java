package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.common.config.Constant;
import com.tiza.webservice.common.model.SendMSG;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description: CMD_82
 * Author: DIYILIU
 * Update: 2016-03-21 14:51
 */

@Service
public class CMD_82 extends M2DataProcess {

    public CMD_82() {
        this.cmd = 0x82;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void parse(byte[] content, Header header) {

        ByteBuf buf = Unpooled.copiedBuffer(content);
        int serial = buf.readUnsignedShort();
        int cmd = buf.readByte();
        int result = buf.readByte();

        if (waitRespCacheProvider.containsKey(serial)) {
            SendMSG sendMSG = (SendMSG) waitRespCacheProvider.get(serial);
            waitRespCacheProvider.remove(serial);

            // 更新数据库状态
            String sql = "UPDATE bs_instructionlog t" +
                    " SET t.sendstatus = ?," +
                    " t.responsetime = ?" +
                    " WHERE t.id = ?";

            jdbcTemplate.update(sql, new Object[]{result == 0 ? Constant.SendStatus.SUCCESS : Constant.SendStatus.FAIL,
                    new Date(), sendMSG.getId()});
        }
    }


}
