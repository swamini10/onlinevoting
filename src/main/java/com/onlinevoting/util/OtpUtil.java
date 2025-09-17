package com.onlinevoting.util;

import java.util.Random;

public class OtpUtil {

    public static Integer generateOtp() {
      Random random = new Random();
      return 10000 + random.nextInt(90000);
    }

}
