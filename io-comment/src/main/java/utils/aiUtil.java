package utils;

import java.util.UUID;

public class aiUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
