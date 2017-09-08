package com.tiza.process.gb32960.protocol.cmd;

import com.tiza.process.common.support.model.Header;
import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.gb32960.protocol.GB32960DataProcess;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Date;

/**
 * Description: CMD_02
 * Author: Wangw
 * Update: 2017-09-07 14:57
 */
public class CMD_02 extends GB32960DataProcess {

    public CMD_02() {
        this.cmd = 0x02;
    }

    @Override
    public void parse(byte[] content, Header header) {
        ByteBuf buf = Unpooled.copiedBuffer(content);

        byte[] dateBytes = new byte[6];
        buf.readBytes(dateBytes);
        Date date = CommonUtil.bytesToDate(dateBytes);

        int flag = buf.readByte();

        switch (flag) {

            case 0x01:
                parseVehicle(buf);
                break;
            case 0x02:

                break;
            case 0x03:

                break;
            case 0x04:

                break;
            case 0x05:

                break;
            case 0x06:

                break;
            case 0x07:

                break;
            default:
                break;

        }
    }

    private void parseVehicle(ByteBuf byteBuf){



    }

    private void parseMotor(ByteBuf byteBuf){



    }

    private void parseBattery(ByteBuf byteBuf){



    }

    private void parseEngine(ByteBuf byteBuf){



    }

    private void parsePosition(ByteBuf byteBuf){



    }

    private void parseExtreme(ByteBuf byteBuf){



    }

    private void parseAlarm(ByteBuf byteBuf){



    }
}
