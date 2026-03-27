package com.atria.userservice.util;

import java.util.Random;

public class RandomGeneraterUtil {

    public static long generateTenDigitNumber() {
        Random random = new Random();
        return 1_000_000_000L + (long)(random.nextDouble() * 9_000_000_000L);
    }
}
