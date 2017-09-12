import com.tiza.gateway.common.util.CommonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2017-09-12 14:52
 */
public class TestMain {


    @Test
    public void test(){


        String str = "232301FE415445535431313830363137313636363501001E11090C0E141A001D38393836303631373038303031323438323030340100BB";

        ByteBuf buf = Unpooled.copiedBuffer(CommonUtil.hexStringToBytes(str));

        buf.readByte();
        buf.readByte();
        buf.readByte();


        System.out.println(buf.readUnsignedByte());
        System.out.println(buf.readUnsignedByte());
        System.out.println(0xFE);

    }
}
