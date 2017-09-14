package com.tiza.webservice.protocol.m2.cmd;

import com.diyiliu.common.model.Header;
import com.diyiliu.common.util.CommonUtil;
import com.tiza.webservice.protocol.bean.M2Header;
import com.tiza.webservice.protocol.m2.M2DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description: CMD_04
 * Author: DIYILIU
 * Update: 2016-03-30 11:29
 */

@Service
public class CMD_04 extends M2DataProcess {

    public CMD_04() {
        this.cmd = 0x04;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        byte[] bytes = new byte[0];

        // 单个参数
        if (argus[0] instanceof Map) {
            Map paramMap = (Map) argus[0];
            int paramId = (int) paramMap.get("id");
            Object paramValue = paramMap.get("value");

            bytes = toParse(paramId, paramValue);
        }else if (argus[0] instanceof List){
            List list = (List) argus[0];

            byte[][] array = new byte[list.size()][];
            for (int i = 0; i < list.size(); i++){
                Map paramMap = (Map) list.get(i);

                int paramId = (int) paramMap.get("id");
                Object paramValue = paramMap.get("value");

                array[i] = toParse(paramId, paramValue);
            }
            ByteBuf buf = Unpooled.copiedBuffer(array);

            bytes = buf.array();
        }

        return headerToSendBytes(bytes, cmd, m2Header);
    }

    public  byte[] toParse(int paramId, Object paramVal){

        byte[] value;
        switch (paramId) {
            case 0x06:
                value = CommonUtil.ipToBytes((String) paramVal);
                break;
            case 0x08:
                value = CommonUtil.ipToBytes((String) paramVal);
                break;
            case 0x0A:
                value = CommonUtil.longToBytes((Integer) paramVal, 2);
                break;
            case 0x0D:
                value = CommonUtil.longToBytes((Integer) paramVal, 2);
                break;
            case 0x12:
                value = String.valueOf(paramVal).getBytes();
                break;
            case 0x13:
                value = CommonUtil.longToBytes((Integer) paramVal, 4);
                break;
            case 0x18:
                value = CommonUtil.longToBytes((Integer) paramVal, 4);
                break;
            default:
                value = new byte[0];
        }
        ByteBuf buf = Unpooled.buffer(2 + 1 + value.length);
        buf.writeShort(paramId);
        buf.writeByte(value.length);
        buf.writeBytes(value);

        return buf.array();
    }
}
