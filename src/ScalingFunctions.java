import java.awt.image.BufferedImage;


public class ScalingFunctions {
    // initialize map arrays to size 0 to fail fast if setMaps is not called prior to scale().
    public static int[] columnMap = new int[0];
    public static int[] rowMap = new int[0];

    /**
     * Helper function to calculate the source to target ratio.
     */
    private static double calculateRatio(int sourceValue, int targetValue) {
        return (double)sourceValue / (double)targetValue;
    }

    /**
     * Helper function to generate a new Buffered image of the given height and width.
     */
    private static BufferedImage generateTarget(int height, int width) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Helper function to map target indices to source indices based on the source to target ratio.
     */
    private static int mapByRatio(int targetValue, double sourceToTargetRatio) {
        return (int)Math.floor((double)targetValue * sourceToTargetRatio);
    }
    /**
     * Set up quick map arrays to prevent repetitive calculations for repeated resizing.
     * @param sourceHeight the height in pixels of the source image.
     * @param sourceWidth the width in pixels of the source image.
     * @param targetHeight the height in pixels of the target image.
     * @param targetWidth the width in pixels of the target image.
     */
    public static void setMaps(int sourceHeight, int sourceWidth, int targetHeight, int targetWidth) {
        columnMap = new int[targetWidth];
        rowMap = new int[targetHeight];
        double heightRatio = calculateRatio(sourceHeight, targetHeight);
        double widthRatio = calculateRatio(sourceWidth, targetWidth);
        for (int h = 0; h < targetHeight; ++h) rowMap[h] = mapByRatio(h, heightRatio);
        for (int w = 0; w < targetWidth; ++w) columnMap[w] = mapByRatio(w, widthRatio);
    }

    /**
     * Scale the given source image based on preset maps.
     * @param source the image to be scaled.
     * @return the scaled image.
     */
    public static BufferedImage mapScale(BufferedImage source) {
        if (columnMap.length == 0 || rowMap.length == 0) //fail fast if called without setting maps first
            throw new IllegalStateException("setMaps() must be called prior to mapScale()");
        BufferedImage target = generateTarget(rowMap.length, columnMap.length);
        for (int r = 0; r < rowMap.length; ++r) {
            for (int c = 0; c < columnMap.length; ++c) {
                target.setRGB(
                        c,
                        r,
                        source.getRGB(
                                columnMap[c],
                                rowMap[r]
                        )
                );
            }
        }
        return target;
    }

    /**
     * Scale the given source image to the given height and width.
     * @param source the image to be scaled.
     * @param targetHeight the desired height of the scaled image.
     * @param targetWidth the desired width of the scaled image.
     * @return the scaled image.
     */
    public static BufferedImage quickScale(BufferedImage source, int targetHeight, int targetWidth) {
        BufferedImage target = generateTarget(targetHeight, targetWidth);
        double heightRatio = calculateRatio(source.getHeight(), targetHeight);
        double widthRatio = calculateRatio(source.getWidth(), targetWidth);
        for (int r = 0; r < targetHeight; ++r) {
            for (int c = 0; c < targetWidth; ++c) {
                target.setRGB(
                        c,
                        r,
                        source.getRGB(
                                mapByRatio(c, widthRatio),
                                mapByRatio(r, heightRatio)
                        )
                );
            }
        }
        return target;
    }
}
