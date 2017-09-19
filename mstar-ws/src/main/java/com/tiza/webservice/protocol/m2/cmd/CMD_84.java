package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.diyiliu.common.util.CommonUtil;
import com.diyiliu.common.util.JacksonUtil;
import com.tiza.webservice.common.config.Constant;
import com.tiza.webservice.common.model.SendMSG;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: CMD_84
 * Author: DIYILIU
 * Update: 2016-03-29 9:26
 */

@Service
public class CMD_84 extends M2DataProcess {

    public CMD_84() {
        this.cmd = 0x84;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void parse(byte[] content, Header header) {
        M2Header m2Header = (M2Header) header;

        ByteBuf buf = Unpooled.copiedBuffer(content);
        if (buf.readableBytes() < 3) {
            return;
        }
        int result = buf.readByte();
        int paramId = buf.readUnsignedShort();

        String terminalId = m2Header.getTerminalId();
        int serial = Integer.parseInt(terminalId.substring(terminalId.length() - 4)) + 0x07 + paramId;
        if (waitRespCacheProvider.containsKey(serial)) {
            SendMSG sendMSG = (SendMSG) waitRespCacheProvider.get(serial);

            String value = "";
            if (result == 0) {
                int length = buf.readByte();
                byte[] bytes = new byte[length];
                buf.readBytes(bytes);
                value = toValue(paramId, bytes);

                //logger.info("终端参数ID[{}]，参数值[{}], 原始值[{}]", CommonUtil.toHex(paramId), value, CommonUtil.bytesToString(bytes));
            }
            waitRespCacheProvider.remove(serial);

            // 更新数据库状态
            Map resultMap = new HashMap();
            resultMap.put("id", paramId);
            resultMap.put("value", value);

            // 更新数据库状态
            String sql = "UPDATE bs_instructionlog t" +
                    " SET t.sendstatus = ?," +
                    " t.responsetime = ?," +
                    " t.responsedata = ?" +
                    " WHERE t.id = ?";

            jdbcTemplate.update(sql, new Object[]{result == 0 ? Constant.SendStatus.SUCCESS : Constant.SendStatus.FAIL,
                    new Date(), JacksonUtil.toJson(resultMap), sendMSG.getId()});
        }
    }

    private String toValue(int paramId, byte[] bytes) {

        String value;
        switch (paramId) {
            case 0x06:
                value = CommonUtil.bytesToIp(bytes);
                break;
            case 0x08:
                value = CommonUtil.bytesToIp(bytes);
                break;
            case 0x0A:
                value = CommonUtil.bytesToLong(bytes) + "";
                break;
            case 0x0D:
                value = CommonUtil.bytesToLong(bytes) + "";
                break;
            case 0x12:
                value = new String(bytes);
                break;
            case 0x13:
                value = CommonUtil.bytesToLong(bytes) + "";
                break;
            case 0x18:
                value = CommonUtil.bytesToLong(bytes) + "";
                break;
            default:
                value = "";
        }

        return value;
    }
}
