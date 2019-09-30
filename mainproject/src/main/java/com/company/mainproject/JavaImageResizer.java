package com.company.mainproject;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class will resize all the images in a given folder
 *
 * @author pankaj
 */
public class JavaImageResizer {

    public void resizeDirectoryImages() throws IOException {
        String imagesPath = System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/image/";
        String resizedImagesPath = System.getProperty("user.dir") + "/src/main/resources/static/image/";

        File directory = new File(resizedImagesPath);

        if (!directory.exists()) {
            directory.mkdir();
        }

        File folder = new File(imagesPath);
        File[] listOfFiles = folder.listFiles();
        Image img = null;
        BufferedImage tempJPG = null;
        File newFileJPG = null;

        // TODO: 29.09.2019  set timeout while resources are loading!!!
        for (int i = 0; i < listOfFiles.length; i++) {
            boolean isImageResized = new File(resizedImagesPath, listOfFiles[i].getName()).exists();

            if (listOfFiles[i].isFile() && !isImageResized) {
                img = ImageIO.read(new File(imagesPath + listOfFiles[i].getName()));
                tempJPG = resizeImage(img, 200, 200);
                newFileJPG = new File(resizedImagesPath + listOfFiles[i].getName());
                ImageIO.write(tempJPG, "jpg", newFileJPG);
            }
        }
        System.out.println("DONE");
    }

    /**
     * This function resize the image file and returns the BufferedImage object that can be saved to file system.
     */
    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}