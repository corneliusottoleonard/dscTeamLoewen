import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExampleJavaApp {

    public static void main(String[] args) {
        // Create a 300x300 image
        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context
        Graphics g = image.getGraphics();

        // Draw a simple red square
        g.setColor(Color.RED);
        g.fillRect(50, 50, 200, 200);

        // Dispose of the graphics context
        g.dispose();

        // Save the image as a PNG file
        try {
            File outputFile = new File("output.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
