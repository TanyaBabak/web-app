package com.babak.utils;

import java.util.Locale;


public class LocaleUtils {

    public static Locale toLocale(String inputLocale) {

        final int lengthInputLocale = inputLocale.length();

        if (lengthInputLocale < 2) {
            throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
        }

        final char ch0 = inputLocale.charAt(0);

        if (ch0 == '_') {
            if (lengthInputLocale < 3) {
                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
            }

            final char ch1 = inputLocale.charAt(1);
            final char ch2 = inputLocale.charAt(2);

            if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) {
                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
            }

            if (lengthInputLocale == 3) {
                return new Locale("", inputLocale.substring(1, 3));
            }

            if (lengthInputLocale < 5) {
                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
            }

            if (inputLocale.charAt(3) != '_') {
                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
            }

            return new Locale("", inputLocale.substring(1, 3), inputLocale.substring(4));
        }

        final String[] split = inputLocale.split("_", -1);
        final int occurrences = split.length - 1;

        switch (occurrences) {
            case 0:
                if (LocaleUtils.isAllLowerCase(inputLocale) && (lengthInputLocale == 2 || lengthInputLocale == 3)) {
                    return new Locale(inputLocale);
                }

                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);

            case 1:
                if (LocaleUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && split[1].length() == 2 && LocaleUtils.isAllUpperCase(split[1])) {
                    return new Locale(split[0], split[1]);
                }

                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);

            case 2:
                if (LocaleUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && (split[1].length() == 0 || (split[1].length() == 2 && LocaleUtils.isAllUpperCase(split[1]))) && split[2].length() > 0) {
                    return new Locale(split[0], split[1], split[2]);
                }

                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);

            default:
                throw new IllegalArgumentException("Invalid locale format: " + inputLocale);
        }
    }

    private static boolean isAllLowerCase(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return false;
        }

        final int lengthCharSequence = charSequence.length();

        for (int i = 0; i < lengthCharSequence; i++) {
            if (Character.isLowerCase(charSequence.charAt(i)) == false) {
                return false;
            }
        }

        return true;
    }

    private static boolean isAllUpperCase(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return false;
        }

        final int lengthCharSequence = charSequence.length();

        for (int i = 0; i < lengthCharSequence; i++) {
            if (Character.isUpperCase(charSequence.charAt(i)) == false) {
                return false;
            }
        }

        return true;
    }
}
