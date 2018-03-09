package fr.paragoumba.hidemessageinimage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HideMessageInImage {

    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        if (args.length < 4){

            System.out.println("Relaunch the app with the command java -jar HideMessageInImage.jar <imageURL> <outputImageURL> <hideinblue | metadata> <\"messageWithSpaces\" | messageWithoutSpaces>");
            return;

        }

        File image = new File(args[0]);
        File newImage = new File(args[1]);
        String method = args[2] != null ? args[2] : "";

        if (!image.exists()){

            System.out.println("Image doesn't exists.");
            return;

        }

        String[] messageParts = new String[args.length - 3];

        System.arraycopy(args, 3, messageParts, 0, args.length - 3);

        StringBuilder message = new StringBuilder();
        args[0] = args[0].substring(args[0].indexOf("\"") + 1);

        for (String messagePart : messageParts){

            if (messagePart.contains("\"")){

                message.append(messagePart.substring(0, messagePart.indexOf("\"")));
                break;

            }

            message.append(messagePart);

        }

        System.out.println("Message : '" + message + "' " + message.length() + "chars (" + message.length() * 16 + "bits)");

        switch (method){

            case "metadata":

                System.out.println("Hiding in metadatas.");
                hideInMeta(image, newImage, message.toString());
                break;

            default:

                System.out.println("Hiding in blue.");
                addBinaryMessageToBlueValue(image, newImage, message.toString());
                break;

        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Executed in " + elapsed / 1E3 + "s (" + elapsed + "ms)");

    }

    private static void addBinaryMessageToBlueValue(File image, File newImage, String message) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(image);
        Graphics2D gImage = bufferedImage.createGraphics();
        String[] control = Integer.toBinaryString(1234567).split("");
        int i = 0;
        int imageLength = bufferedImage.getWidth() * bufferedImage.getHeight();
        int usablePixels = imageLength - control.length;
        usablePixels -= usablePixels % Character.SIZE;

        System.out.println("Max chars : " + usablePixels / Character.SIZE + "chars (" + usablePixels + "bits)");

        byte[] bits = message.getBytes();

        if (bits.length > usablePixels){

            System.out.println("Message is too long ! " + bits.length / Character.SIZE + "/" + usablePixels / Character.SIZE + "chars (" + bits.length + "/" + usablePixels + "bits)");
            return;

        }

        System.out.print(message + ":");

        for (byte b : message.getBytes(Charset.forName("UTF-" + Character.SIZE))) System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0"));

        System.out.println();

        bits = getBits(bits, imageLength);

        for (int y = 0; y < bufferedImage.getHeight(); ++y){
            for (int x = 0; x < bufferedImage.getWidth(); ++x) {

                Color color = new Color(bufferedImage.getRGB(x, y), true);

                gImage.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue() - color.getBlue() % 2 + Byte.parseByte(control[x]), color.getAlpha()));
                gImage.drawRect(x, y, 0, 0);

                ++i;

                if (i >= control.length) break;

            }

            if (i >= control.length) break;

        }

        System.out.println("Writing control : ok");

        System.out.print("bits:");
        for (byte b : bits) System.out.print(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0").substring(7));

        System.out.println();

        int x = control.length;
        i = 0;

        System.out.print("bits[i]:");

        for (int y = 0; y < bufferedImage.getHeight(); ++y) {
            for (; x < bufferedImage.getWidth(); ++x) {

                Color color = new Color(bufferedImage.getRGB(x, y), true);
                color = new Color(color.getRed(), color.getGreen(), color.getBlue() - color.getBlue() % 2 + bits[i], color.getAlpha());

                System.out.print(bits[i]);

                gImage.setColor(color);
                gImage.drawRect(x, y, 0, 0);

                i++;

            }

            x = 0;

        }

        System.out.println("i:" + i + ";bits:" + bits.length);

        ImageIO.write(bufferedImage, image.getName().substring(image.getName().lastIndexOf(".") + 1).toUpperCase(), newImage);
        System.out.println("Writing message : ok");

    }

    private static void hideInMeta(File image, File newImage, String message){



    }

    private static byte[] getBits(byte[] bytes, int length){

        byte[] newBytes = new byte[length];

        int i = 0;
        
        for (; i < bytes.length; ++i){

            String[] bits = String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0").split("");

            for (int j = 0; j < 8; ++j) newBytes[i * 8 + j] = Byte.parseByte(bits[j]);
            
        }
        
        for (; i < length; ++i) newBytes[i] = 0;

        return newBytes;

    }
}
