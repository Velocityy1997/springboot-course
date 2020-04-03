package io.ken.springboot.course.common.utils;

import java.util.UUID;

/**
 * @author GouYudong
 * @create 2020-01-07 16:56
 **/
public class UUIDUtils {

    /**
     *32位默认长度的uuid
     * (获取32位uuid)
     *
     * @return
     */
    public static  String getUUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     *
     * (获取指定长度uuid)
     *
     * @return
     */
    public static  String getUUID(int len) {
        if (0 >= len) {
            return null;
        }

        String uuid = getUUID();
        System.out.println(uuid);
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }

        return str.toString();
    }

}
