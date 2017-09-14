package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.tiza.webservice.common.config.Constant;
import com.tiza.webservice.common.model.SendMSG;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description: CMD_83
 * Author: DIYILIU
 * Update: 2016-03-21 14:51
 */

@Service
public class CMD_83 extends M2DataProcess {

    public CMD_83() {
        this.cmd = 0x83;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void parse(byte[] content, Header header) {
        M2Header m2Header = (M2Header) header;

        /**
         * 只更新状态，不解析位置信息。
         */

        String terminalId = m2Header.getTerminalId();
        int serial = Integer.parseInt(terminalId.substring(terminalId.length() - 4)) + 0x03;
        if (waitRespCacheProvider.containsKey(serial)) {
            SendMSG sendMSG = (SendMSG) waitRespCacheProvider.get(serial);
            waitRespCacheProvider.remove(serial);

            // 更新数据库状态
            String sql = "UPDATE bs_instructionlog t" +
                    " SET t.sendstatus = ?," +
                    " t.responsetime = ?" +
                    " WHERE t.id = ?";

            jdbcTemplate.update(sql, new Object[]{Constant.SendStatus.SUCCESS,
                    new Date(), sendMSG.getId()});
        }
    }


}
