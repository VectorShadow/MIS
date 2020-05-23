import java.awt.image.BufferedImage;

public class ScalingFunctions {
    public static int[] columnMap;
    public static int[] rowMap;

    public static void setMaps(int sourceHeight, int sourceWidth, int targetHeight, int targetWidth) {
        columnMap = new int[targetWidth];
        rowMap = new int[targetHeight];
        double heightRatio = (double)sourceHeight / (double)targetHeight;
        double widthRatio = (double)sourceWidth / (double)targetWidth;
        for (int h = 0; h < targetHeight; ++h) rowMap[h] = (int)Math.floor((double)h * heightRatio);
        for (int w = 0; w < targetWidth; ++w) columnMap[w] = (int)Math.floor((double)w * widthRatio);
    }

    public static BufferedImage scale(BufferedImage source) {
        BufferedImage target = new BufferedImage(columnMap.length, rowMap.length, BufferedImage.TYPE_INT_RGB);
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
}
