package com.stopo.utils;

public class CpfValidator {

    public static boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return true;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            int weight = 10;
            for (int i = 0; i < 9; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }
            int remainder = 11 - (sum % 11);
            int digit1 = (remainder == 10 || remainder == 11) ? 0 : remainder;

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                sum += (cpf.charAt(i) - '0') * weight--;
            }
            remainder = 11 - (sum % 11);
            int digit2 = (remainder == 10 || remainder == 11) ? 0 : remainder;

            return digit1 == (cpf.charAt(9) - '0') && digit2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return "-";
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                    cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        }
        return cpf;
    }
}