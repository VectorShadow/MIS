import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestDriver {

    private static final String PNG = "PNG";
    private static final String SOURCE_PATH = "./images/source.png";
    private static final String TARGET_PATH = "./images/target.png";

    private static final int TARGET_HEIGHT = 1280;
    private static final int TARGET_WIDTH = 800;

    public static void main(String[] args) throws IOException {
        BufferedImage sourceImage = ImageIO.read(new File(SOURCE_PATH));

        //set maps test
        long start = System.currentTimeMillis();
        ScalingFunctions.setMaps(sourceImage.getHeight(), sourceImage.getWidth(), TARGET_HEIGHT, TARGET_WIDTH);
        long stop = System.currentTimeMillis();
        System.out.println("Set maps in " + (stop - start) + " ms.");

        //scale with preset maps test
        start = System.currentTimeMillis();
        BufferedImage targetImage = ScalingFunctions.mapScale(sourceImage);
        stop = System.currentTimeMillis();
        System.out.println("Scaled image with preset maps in " + (stop - start) + " ms.");
        ImageIO.write(targetImage, PNG, new File(TARGET_PATH));

        //scale without preset maps test
        start = System.currentTimeMillis();
        targetImage = ScalingFunctions.quickScale(sourceImage, TARGET_HEIGHT, TARGET_WIDTH);
        stop = System.currentTimeMillis();
        System.out.println("Scaled image without preset maps in " + (stop - start) + " ms.");

        //repeat scaling with preset maps test
        start = System.currentTimeMillis();
        for (int i = 0; i < 64; ++i) {
            targetImage = ScalingFunctions.mapScale(sourceImage);
        }
        stop = System.currentTimeMillis();
        System.out.println("Scaled image with preset maps repeatedly in " + (stop - start) + " ms.");

        //repeat scaling without preset maps test
        start = System.currentTimeMillis();
        for (int i = 0; i < 64; ++i) {
            targetImage = ScalingFunctions.quickScale(sourceImage, TARGET_HEIGHT, TARGET_WIDTH);
        }
        stop = System.currentTimeMillis();
        System.out.println("Scaled image without preset maps repeatedly in " + (stop - start) + " ms.");
    }
}
