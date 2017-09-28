import com.tiza.op.util.DateUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2017-09-28 15:04
 */
public class TestMain {

    @Test
    public  void test(){

        Calendar calendar = Calendar.getInstance();


        String str = DateUtil.date2Str(calendar.getTime());

        System.out.println(str);

        Date date = DateUtil.str2Date(str);

        System.out.println(date);
    }
}
