package org.example.view.enums;

import java.util.Random;

public enum CaptchaAsciiArt {

    ZERO("::'#####:::\n" +
            ":'##.. ##::\n" +
            "'##:::: ##:\n" +
            " ##:::: ##:\n" +
            " ##:::: ##:\n" +
            ". ##:: ##::\n" +
            ":. #####:::\n" +
            "::.....::::",0),
    ONE(":::'##:::::\n" +
            ":'####:::::\n" +
            ":.. ##:::::\n" +
            "::: ##:::::\n" +
            "::: ##:::::\n" +
            "::: ##:::::\n" +
            ":'######:::\n" +
            ":......::::",0),
    TWO(":'#######::\n" +
            "'##.... ##:\n" +
            "..::::: ##:\n" +
            ":'#######::\n" +
            "'##::::::::\n" +
            " ##::::::::\n" +
            " #########:\n" +
            ".........::",2),
    Three(":'#######::\n" +
            "'##.... ##:\n" +
            "..::::: ##:\n" +
            ":'#######::\n" +
            ":...... ##:\n" +
            "'##:::: ##:\n" +
            ". #######::\n" +
            ":.......:::",3),
    FOUR("'##::::::::\n" +
            " ##:::'##::\n" +
            " ##::: ##::\n" +
            " ##::: ##::\n" +
            " #########:\n" +
            "...... ##::\n" +
            ":::::: ##::\n" +
            "::::::..:::",4),
    FIVE("'########::\n" +
            " ##.....:::\n" +
            " ##::::::::\n" +
            " #######:::\n" +
            "...... ##::\n" +
            "'##::: ##::\n" +
            ". ######:::\n" +
            ":......::::",5),
    SIX(":'#######::\n" +
            "'##.... ##:\n" +
            " ##::::..::\n" +
            " ########::\n" +
            " ##.... ##:\n" +
            " ##:::: ##:\n" +
            ". #######::\n" +
            ":.......:::",6),
    SEVEN("'########::\n" +
            " ##..  ##::\n" +
            "..:: ##::::\n" +
            "::: ##:::::\n" +
            ":: ##::::::\n" +
            ":: ##::::::\n" +
            ":: ##::::::\n" +
            "::..:::::::",7),
    EIGHT(":'#######::\n" +
            "'##.... ##:\n" +
            " ##:::: ##:\n" +
            ": #######::\n" +
            "'##.... ##:\n" +
            " ##:::: ##:\n" +
            ". #######::\n" +
            ":.......:::",8),
    NINE(":'#######::\n" +
            "'##.... ##:\n" +
            " ##:::: ##:\n" +
            ": ########:\n" +
            ":...... ##:\n" +
            "'##:::: ##:\n" +
            ". #######::\n" +
            ":.......:::",9);

    private final String asciiArt;
    private final int digit;
    public static int captchaValue=0;

    CaptchaAsciiArt(String asciiArt, int digit) {
        this.asciiArt = asciiArt;
        this.digit = digit;
    }

    private static String getAsciiArt(int number){
        return CaptchaAsciiArt.values()[number].asciiArt;
    }
    public static String captchaGenerator(){
        String output="";
        captchaValue = 0;
        Random random = new Random();
        int digitsCount = random.nextInt(5)+4;
        for (int i=0;i<digitsCount;i++){
            int randomDigit = random.nextInt(10);
            while (i==0&&randomDigit==0)
                randomDigit = random.nextInt(10);
            output+= randomNoise(getAsciiArt(randomDigit))+"\n\n";
            captchaValue=captchaValue*10+randomDigit;
        }
        return output;
    }
    private static String randomNoise(String input){
        char[] tempOutput =new char[input.length()];
        int lineCounter=-1;
        int randomChar=0;
        int randomChar2=0;
        Random random = new Random();

        for (int i=0;i<input.length();i++){
            if (i%12==0){
                randomChar=random.nextInt(11);
                randomChar2=random.nextInt(11);
                lineCounter++;
            }
            if ((i==(lineCounter*12+randomChar))||(i==(lineCounter*12+randomChar2))){
                tempOutput[i]=' ';
            }else tempOutput[i]=input.charAt(i);
        }
        return new String(tempOutput);
    }

}
