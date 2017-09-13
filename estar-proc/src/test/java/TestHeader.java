import com.tiza.process.common.util.CommonUtil;
import com.tiza.process.gb32960.bean.GB32960Header;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Description: TestHeader
 * Author: Wangw
 * Update: 2017-09-07 10:09
 */
public class TestHeader {


    @Test
    public void test1(){

        GB32960Header header = new GB32960Header();
        header.setCmd(0x01);
        header.setTerminalId("00008613685132382");
        header.setContent(new byte[0]);
        header.setSerial(1);

        ByteBuf buf = Unpooled.buffer(25);

        buf.writeBytes(new byte[]{0x23, 0x23});
        buf.writeByte(0x01);
        buf.writeByte(0xFE);
        buf.writeBytes(header.getTerminalId().getBytes());
        buf.writeByte(0x01);
        buf.writeShort(0);

        byte[] array = new byte[22];
        buf.getBytes(2, array);

        buf.writeByte(CommonUtil.getCheck(array));

        System.out.println(CommonUtil.bytesToStr(buf.array()));
    }

    @Test
    public void test2(){

        String vin = "00008613685132382";

        System.out.println(vin.getBytes().length);
    }


    @Test
    public void test3(){

        String str = "FFFFFFFE";
        byte[] bytes = CommonUtil.hexStringToBytes(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        System.out.println(buf.readUnsignedShort());

        System.out.println(0xfffe);
        System.out.println(0xffff);
    }

    @Test
    public void test4(){

        String str = "FFFFFFFE";
        byte[] bytes = CommonUtil.hexStringToBytes(str);
        ByteBuf buf = Unpooled.copiedBuffer(bytes);

        long l = buf.readUnsignedInt();
        System.out.println(l);

        System.out.println(0xfffffffel == l);
        System.out.println(BigInteger.valueOf(0xFFFFFFFE).longValue() == l);
    }

    @Test
    public void test5(){

        String str = "07D007D000EC0001601BB81BB21BB31BB41BB81BB21BB31BB41BB81BB21BB31BB41BB81BB21BB31BB41BB81BB21BB31BB41BB81BB21BB31BB42BB82BB22BB32BB42DAC2BB22BB32BB42BB82BB22BB32BB42BB82BB22BB32BB42BB82BB22BB32BB42BB82BB22BB32BB43BB83BB23BB33BB43BB83BB23BB33BB43BB83BB23BB33BB43BB83BB23AF03BB43BB83BB23BB33BB43BB83BB23BB33BB44BB84BB24BB34BB44BB84BB24BB34BB44BB84BB24BB34BB44BB84BB24BB34BB44BB84BB24BB34BB44BB84BB24BB34BB409010100183D18F3D1020001003E18F3D1020002003F18F3D10200033A";

        byte[] bytes = CommonUtil.hexStringToBytes(str);
        System.out.println(bytes.length);
    }
}
