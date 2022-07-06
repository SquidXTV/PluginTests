package org.squidxtv.plugintests.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BufferedImageUtils {

    /**
     * @param img image to resize
     * @param width width to resize to
     * @param height height to resize to
     * @return new resized BufferedImage
     */
    public static BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage newSize = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newSize.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return newSize;
    }

    /**
     * split image into a grid of width*height images
     *
     * @param original original BufferedImage
     * @param width number of columns
     * @param height number of rows
     * @return split BufferedImage into a 2D-Array
     */
    public static BufferedImage[][] splitImage(BufferedImage original, int width, int height) {
        BufferedImage[][] parts = new BufferedImage[height][width];
        int heightPerPart = original.getHeight() / height;
        int widthPerPart = original.getWidth() / width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                parts[i][j] = original.getSubimage((j * widthPerPart), (i * heightPerPart),
                        widthPerPart, heightPerPart);
            }
        }
        return parts;
    }

    public static BufferedImage deepCopy(BufferedImage original) {
        ColorModel model = original.getColorModel();
        boolean premultiplied = original.isAlphaPremultiplied();
        WritableRaster raster = original.copyData(original.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(model, raster, premultiplied, null);
    }

    @Deprecated
    public static int getBestWidth(BufferedImage image) {
        return (int) Math.round(image.getWidth() / 128.0);
    }

    @Deprecated
    public static int getBestHeight(BufferedImage image) {
        return (int) Math.round(image.getHeight() / 128.0);
    }

    public static BufferedImage loadImageFromPath(String path) throws IOException {
        try (InputStream in = BufferedImageUtils.class.getResourceAsStream(path)) {
            if (in == null) {
                throw new NullPointerException("InputStream in == null");
            }
            return ImageIO.read(in);
        }
    }

    public static BufferedImage loadImageFromURL(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

    public static BufferedImage loadImageFromURL(URL url) throws IOException {
        return ImageIO.read(url);
    }

    public static boolean equalsImage(BufferedImage a, BufferedImage b) {
        if (a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
            return false;
        }

        int width  = a.getWidth();
        int height = a.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (a.getRGB(x, y) != b.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Deprecated
    public static boolean isURL(String pathOrURL) {
        try {
            URL url = new URL(pathOrURL);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
