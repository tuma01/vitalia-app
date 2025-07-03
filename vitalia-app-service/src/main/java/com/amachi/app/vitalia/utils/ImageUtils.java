package com.amachi.app.vitalia.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUtils {

    private ImageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(
                targetWidth,
                targetHeight,
                originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType()
        );

        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        graphics2D.drawImage(
                originalImage,
                0, 0, targetWidth, targetHeight,
                null
        );
        graphics2D.dispose();

        return resizedImage;
    }

    public static boolean isImage(byte[] data) {
        try {
            ImageIO.read(new ByteArrayInputStream(data));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
