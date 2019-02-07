package vip.creeper.mcserverplugins.creeperkits.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by July on 2018/03/10.
 */
public class StrUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static String getCurrentTimeStr() {
        return sdf.format(System.currentTimeMillis());
    }
}
