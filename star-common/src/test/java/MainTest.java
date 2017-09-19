import com.diyiliu.common.model.Circle;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: MainTest
 * Author: DIYILIU
 * Update: 2017-09-19 10:43
 */

public class MainTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test(){

        String str = "[{\"lng\":\"121.458091\",\"lat\":\"31.217368\",\"radius\":\"139.749\"}]";

        JavaType javaType = getCollectionType(ArrayList.class, Circle.class);

        try {
            List<Circle> list = mapper.readValue(str, javaType);
            System.out.println(list.get(0).getLng());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
