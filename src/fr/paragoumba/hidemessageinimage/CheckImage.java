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
        BufferedImage image = ImageIO.read(new File("/home/paragoumba/Bureau/nu11.png"));

        for (int y = 0; y < image.getHeight(); ++y){
            for (; x < image.getWidth(); ++x){

                System.out.print(new Color(image.getRGB(x, y)).getBlue() % 2);
                if (x == image.getWidth()) System.out.println();

                if (image.getWidth() * y + x > controlBits) break;

            }

            if (image.getWidth() * y + x > controlBits) break;

        }
    }
}
