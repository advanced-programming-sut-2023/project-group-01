package org.example.controller;

import org.example.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CaptchaGenerator {
    public static int captchaValue = 1;
    public static void captchaGenerator() throws IOException {
        Random random = new Random();
        captchaValue = random.nextInt(90000) + 10000;
        String code = "from captcha.image import ImageCaptcha\n" +
                "n=\""+captchaValue+"\"\n" +
                "image = ImageCaptcha()\n" +
                "data = image.generate(str(n))\n" +
                "image.write(str(n), str(n) + '.png')";
        FileWriter myWriter = new FileWriter("src\\main\\resources\\CaptchaPictures\\main.py");
        myWriter.write(code);
        myWriter.close();
        Process process = Runtime.getRuntime().exec(Main.commands[Main.user]);


        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
