package com.babak.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Hashing {

    private static final Logger LOGGER = Logger.getLogger(Hashing.class);

    /**
     * Hashing password.
     */
    public static String md5Apache(String st) {

        String md5Hex = DigestUtils
                .md5Hex(st);
        LOGGER.debug("Password was hashed");
        return md5Hex;
    }
}
