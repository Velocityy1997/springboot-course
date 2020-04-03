package io.ken.springboot.course.common.utils;

import java.util.Random;

/**
 * 生成随机的位数可定的数字
 * @author GouYudong
 * @create 2020-01-08 14:13
 **/
public class RandomNumUtils {

    public static   String getRandomCode(Integer codeLength){

        Random random = new Random();
        int num = 0;

        if ( null == codeLength) {
            //6位
            num= random.nextInt(9000)+1000;//899999+100000=999999

        }else {
            if (codeLength > 0) {

                if (codeLength == 6){
                    num= random.nextInt(900000)+100000;//899999+100000=999999

                } else if (codeLength == 4) {
                    num = random.nextInt(9000) + 1000;//8999+1000=9999=1000-9999

                }else if (codeLength == 5) {
                    num = random.nextInt(90000) + 10000;//8999+1000=9999=1000-9999

                } else if (codeLength == 2) {
                    //5-60
                    num = random.nextInt(56) + 5; //55+5

                } else if (codeLength == 3) {
                    //5-60
                    num = random.nextInt(900) + 100; //899+100=100-999

                }else if (codeLength == 1) {
                    num = random.nextInt(3)+1 ;//1~3

                }else if (codeLength == 7) {
                    num= random.nextInt(9000000)+1000000;

                }else {
                    num = 0;
                }
            }
        }

        return  String.valueOf(num);

    }

}
