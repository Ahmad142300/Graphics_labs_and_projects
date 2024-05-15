import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String fileName) {

        // from
//        glClearColor(0, 0, 0, 0);
        glShadeModel(GL_FLAT);
//        glEnable(GL_DEPTH_TEST);

        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);



        BufferedImage bi;
        try {
            bi =  ImageIO.read(new File(fileName));
            this.width = bi.getWidth();
            this.height = bi.getHeight();

            int[] pixelsRAW = new int[this.width * this.height * 4];
            pixelsRAW = bi.getRGB(0, 0, this.width, this.height, null, 0, this.width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(this.width * this.height * 4);

            for (int i = 0; i < this.height; i++) {
//                if (i >= this.height) break;
                for (int j = 0; j < this.width; j++) {
                    int index = i * this.width + j;
                    int pixel = pixelsRAW[index];
//                    System.out.printf(Locale.US, "%5d %5d %8d %5d %5d %10d \n",
//                            i, j, index, this.width, this.height, pixel);
                    pixels.put((byte) ((pixel >> 16) & 0xFF));  // Red
                    pixels.put((byte) ((pixel >> 8) & 0xFF));   // Green
                    pixels.put((byte) (pixel & 0xFF));          // Blue
                    pixels.put((byte) ((pixel >> 24) & 0xFF));  // Alpha

                }
            }
//            System.out.println(pixelsRAW.length);

            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        }
        catch(IOException e) {
            System.out.println("Error with the texture file's path: " + e.getMessage());
//            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
