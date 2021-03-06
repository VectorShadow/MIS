package main;

import java.awt.*;
import java.awt.image.BufferedImage;


public class MapwiseImageScaler {

    /**
     * Helper function to calculate the source to target ratio.
     */
    private static double calculateRatio(int sourceValue, int targetValue) {
        return (double)sourceValue / (double)targetValue;
    }
    
    /**
     * Helper function to map target indices to source indices based on the source to target ratio.
     */
    private static int mapByRatio(int targetValue, double sourceToTargetRatio) {
        return (int)Math.floor((double)targetValue * sourceToTargetRatio);
    }

    /**
     * Find the point in the source image corresponding to the specified point in the target image,
     * given the dimensions of both images.
     */
    public static Point mapTargetToSource(Point targetCoordinates, int sourceHeight, int sourceWidth, int targetHeight, int targetWidth) {
        double heightRatio = calculateRatio(sourceHeight, targetHeight);
        double widthRatio = calculateRatio(sourceWidth, targetWidth);
        return new Point(mapByRatio(targetCoordinates.x, widthRatio), mapByRatio(targetCoordinates.y, heightRatio));
    }

    /**
     * Scale the given source image to the given height and width.
     * @param source the image to be scaled.
     * @param targetHeight the desired height of the scaled image.
     * @param targetWidth the desired width of the scaled image.
     * @return the scaled image.
     */
    public static BufferedImage scale(BufferedImage source, int targetHeight, int targetWidth) {
        BufferedImage target = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < targetHeight; ++r) {
            for (int c = 0; c < targetWidth; ++c) {
                Point sourceCoordinate =
                        mapTargetToSource(
                                new Point(c, r),
                                source.getHeight(),
                                source.getWidth(),
                                targetHeight,
                                targetWidth
                        );
                target.setRGB(
                        c,
                        r,
                        source.getRGB(
                                sourceCoordinate.x,
                                sourceCoordinate.y
                        )
                );
            }
        }
        return target;
    }
}
