package com.stopo.utils;

import java.util.HashMap;
import java.util.Map;

public class BarCodeGen {
    private static final Map<Character, String> DEFAULT_ITF = new HashMap<>();

    // padrao começo e fim
    private static final String Start_PPT = "0000";
    private static final String End_PPT = "100";

    static {
        DEFAULT_ITF.put('0', "00110");
        DEFAULT_ITF.put('1', "10001");
        DEFAULT_ITF.put('2', "01001");
        DEFAULT_ITF.put('3', "11000");
        DEFAULT_ITF.put('4', "00101");
        DEFAULT_ITF.put('5', "10100");
        DEFAULT_ITF.put('6', "01100");
        DEFAULT_ITF.put('7', "00011");
        DEFAULT_ITF.put('8', "10010");
        DEFAULT_ITF.put('9', "01010");
    }
    /*
    * sequencia binaria
    * posicao impar = barra 1 = larga 0=fina
    * posicao par = espaco 1 = largo 0=fina
    */
    public static String genITFBits(String number) {
        if(!number.matches("\\d+")){
            throw new IllegalArgumentException("Codigo deve ter apenas numeros");
        }
        //padrao do ITF de numeros par
        if(number.length() % 2!=0){
            number = "0" + number;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Start_PPT);

        //mesma coisa de debaixo porem de 2 em 2
        for(int i=0; i<number.length(); i+=2){
            char charB = number.charAt(i);
            char charW = number.charAt(i+1);

            String bitB = DEFAULT_ITF.get(charB);
            String bitW = DEFAULT_ITF.get(charW);
            if(bitB == null || bitW == null){
                throw new IllegalArgumentException("char invalido");
            }
            // entrrelace dos 5 numeros 1preto 1branco consecutivamente
            for(int k = 0;k<5; k++) {
                sb.append(bitB.charAt(k));
                sb.append(bitW.charAt(k));
            }
        }

        sb.append(End_PPT);
        return sb.toString();
    }
}
