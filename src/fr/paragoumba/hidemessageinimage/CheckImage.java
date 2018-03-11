package fr.paragoumba.hidemessageinimage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CheckImage {

    public static void main(String[] args) throws IOException {

        int controlBits = 21 + 208;
        int x = 0;
        BufferedImage image = ImageIO.read(new File("D:/Desktop/Dev/Out/hmii/P4r4g0umb4N3w.png"));
        String messageBits = "";

        for (byte b : "test 1 2 test\u2103".getBytes()) messageBits += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0");

        String test = " 01110100011001010111001101110100001000000011000100100000001100100010000001110100011001010111001101110100 111000101000110010000001";
        String test2 = "01110100011001010111001101110100001000000011000100100000001100100010000001110100011001010111001101110100";
        String prefix = "1101011010000111";

        System.out.println(messageBits);
        System.out.println((char) Integer.valueOf("10101010", 2).intValue());

        for (int i = 0; i < messageBits.length(); i += 8){

            String str = messageBits.substring(i, i + 8);

            System.out.print((char) Integer.valueOf(str, 2).intValue());

        }

        System.out.println();

        System.out.print("Image: ");

        String message = "";

        for (int y = 0; y < image.getHeight(); ++y){
            for (; x < image.getWidth(); ++x){

                message += new Color(image.getRGB(x, y)).getBlue() % 2;
                if (x == image.getWidth()) System.out.println();

                if (image.getWidth() * y + x > controlBits || message.endsWith("1010101010101010")) break;

            }

            if (image.getWidth() * y + x > controlBits || message.endsWith("1010101010101010")) break;

        }

        message = message.substring(0, message.length() - 16);

        System.out.println();

        for (int i = 5; i < message.length() - 3; i += 8){

            String str = message.substring(i, i + 8);

            //-System.out.print(str);
            System.out.print((char) Integer.valueOf(str, 2).intValue());

        }
    }
}
