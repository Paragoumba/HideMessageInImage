package fr.paragoumba.hidemessageinimage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Reader {

    public static void main(String[] args) throws IOException {

        if (args.length < 1){

            System.out.println("Relaunch the app with the command java -jar Reader.jar <imageURL>");
            return;

        }

        BufferedImage bufferedImage = ImageIO.read(new File(args[0]));
        String[] bits = new String[21];
        byte[] bytes = new byte[21];

        for (int i = 0; i < bits.length; ++i) bits[i] = "";

        for (int x = 0; x < 21; ++x){

            System.out.println("bits[" + (x - x % 7) / 7 + "] = " + bits[(x - x % 7) / 7] + " += " + new Color(bufferedImage.getRGB(x, 0)).getBlue() % 2);

            bits[(x - x % 7) / 7] += new Color(bufferedImage.getRGB(x, 0)).getBlue() % 2;

        }

        for (int i = 0; i < bits.length; ++i) bits[i] = "0" + bits[i];

        for (byte b : "1234567".getBytes()) System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0") + " ");

        System.out.println();

        for (String str : bits) System.out.print(Byte.parseByte(str) + " ");

        System.out.println();

        for (int i = 0; i < bits.length; ++i){
            
            bytes[i] = Byte.parseByte(bits[i]);
            
        }
        
        int control = 0;

        if (control == 1234567){

            System.out.println("Control is different from 1234567 (" + control + ")");
            return;

        }

        System.out.println(control);
        
        System.out.println("Control : ok");

        String messageBytes = "";

        for (int y = 0; y < bufferedImage.getHeight(); ++y) {
            for (int x = 10; x < bufferedImage.getWidth(); ++x) {

                int b = bufferedImage.getRGB(x, y) & 0xFF % 2;
                messageBytes += b;

            }
        }

        byte[] bytesArray = new byte[messageBytes.length()];
        String[] stringArray = messageBytes.split("");

        for (int j = 0; j < messageBytes.length(); j++){

            bytesArray[j] = Byte.parseByte(stringArray[j]);

        }

        System.out.println(new String(bytesArray));

    }
}
