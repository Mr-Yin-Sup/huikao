package cn.mryin.driveexam.utils;



import java.security.SecureRandom;
import java.util.Random;

/**
 * @Classname VerCodeGenerateUtil
 * @Description 生成验证码工具类
 * @Date 2021/9/7 14:23
 * @Created by thx
 */
public class VerCodeGenerateUtil {
    private static final String SYMBOLS = "0123456789";//ABCDEFGHIGKLMNOPQRSTUVWXYZ
    private static final Random RANDOM = new SecureRandom();
    //    生成 6 位数的随机数字
    public static String generateVerCode() {
        char[] numbers = new char[6];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(numbers);
    }
}

