package com.hionstudios;

import java.security.SecureRandom;
import java.util.logging.Logger;

/**
 * This file used to generate a random code that can be used at multiple places
 */
public enum Generator {
    OTP(6, "0123456789");
    private final int length;
    private final String charset;
    private static final Logger LOGGER = Logger.getLogger(Generator.class.getName());

    /**
     * @param length  exact length of the code to be generated
     * @param charset the list of all characters that can be present in the code
     */
    Generator(int length, String charset) {
        this.length = length;
        this.charset = charset;
    }

    /**
     * This method generates the random code
     *
     * @return the generated code
     */
    public String generate() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(charset.charAt(random.nextInt(charset.length())));
        }
        return sb.toString();
    }
}
