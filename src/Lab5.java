import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Lab5 {  // Replace "Lab5" with your file name

    private long window;  // The window handle

    public static void main(String[] args) {
        new Lab5().run();  // Replace "Lab5" with your file name
    }

    private void loop() {

        /* This line is critical for LWJGL's interoperation with GLFW's
           OpenGL context, or any context that is managed externally.
           LWJGL detects the context that is current in the current thread,
           creates the GLCapabilities instance and makes the OpenGL
           bindings available for use. */
        GL.createCapabilities();

        // Enabling the texture
        glEnable(GL_TEXTURE_2D);

        Texture texture = new Texture("./assets/background.jpg");
        Texture texture2 = new Texture("./assets/orange-character.png");
        Texture texture3 = new Texture("./assets/green-character-flipped.png");

        //  Fix the aspect ratio
        float scale = 0.0048f;
        float yScale = 0.009f;
        glScalef(scale, yScale, scale);

        float transparent = 0;
        float opaque = 1f;  // opaque = Not transparent
        
        
        /* Run the rendering loop until the user has attempted to close
           the window or has pressed the ESCAPE key. */
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);  // Clear the frame buffer




            glPushMatrix(); {  // Start of drawing the background

                texture.bind();

                glBegin(GL_QUADS); {

                    glColor4f(1, 1, 1, opaque);  // White color to display the texture image correctly. opaque = Not transparent
//                    glColor4f(1, 0, 0, opaque);  // To add a custom color (Red) to the texture

                    glTexCoord2f(1, 0);
                    glVertex2f(200f, 100f);

                    glTexCoord2f(0, 0);
                    glVertex2f(-200f, 100f);

                    glTexCoord2f(0, 1);
                    glVertex2f(-200f, -100f);

                    glTexCoord2f(1, 1);
                    glVertex2f(200f, -100f);

                } glEnd();

            } glPopMatrix();  // End of drawing the background




            glPushMatrix(); {  // Start of drawing the first (Orange) player

                glTranslatef(-80, -45, 0);  // Move the drawing cursor to the left and down

                texture2.bind();

                glBegin(GL_POLYGON); {

                    glColor4f(1, 1, 1, opaque);  // White color to display the texture image correctly. opaque = Not transparent
//                    glColor4f(0, 1, 0, opaque);  // To add a custom color (green) to the texture

                    glTexCoord2f(1, 0);
                    glVertex2f(15, 20);

                    glTexCoord2f(0, 0);
                    glVertex2f(-15, 20);

                    glTexCoord2f(0, 1);
                    glVertex2f(-15, -20);

                    glTexCoord2f(1, 1);
                    glVertex2f(15, -20);

                } glEnd();

            } glPopMatrix();  // End of drawing the first (Orange) player




            glPushMatrix(); {  // Start of drawing the second (Green) player

                glTranslatef(80, -45, 0);  // Move the drawing cursor to the right and down

                texture3.bind();

                glBegin(GL_POLYGON); {

                    glColor4f(1, 1, 1, 0.5f);  // White color to display the texture image correctly. 0.5f = half transparent
//                    glColor4f(0,0, 1, opaque);  // To add a custom color (Blue) to the texture

                    glTexCoord2f(1, 0);
                    glVertex2f(15, 20);

                    glTexCoord2f(0, 0);
                    glVertex2f(-15, 20);

                    glTexCoord2f(0, 1);
                    glVertex2f(-15, -20);

                    glTexCoord2f(1, 1);
                    glVertex2f(15, -20);

                } glEnd();

            } glPopMatrix();  // End of drawing the second (Green) player




            glfwSwapBuffers(window); // swap the color buffers

            /* Poll for window events. The key callback above will only be
               invoked during this call. */
            glfwPollEvents();
        }
    }




    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        int pad = 100;

        int[] fullscreen = {vidmode.width(), vidmode.height()};
        int[] fullscreenPadded = {vidmode.width() - pad, vidmode.height() - pad};
        int[] half = {vidmode.width() / 2, vidmode.height() / 2};
        int[] threeQuarters = {(int) (vidmode.width() / 1.3333333333333333), (int) (vidmode.height() / 1.3333333333333333)};
        int[] ar = {(int) (vidmode.width() / 1.3333333333333333), (int) (vidmode.height() / 1.1)};
        int[] ar2 = {(int) (vidmode.width() / 9), (int) (vidmode.height() / 16)};

        int[] square = {(int) (vidmode.height() / 1.1), (int) (vidmode.height() / 1.1)};


        // Select a different screen resolution by replacing the variable below with one from the above variables
        int[] preferredResolution = threeQuarters;


        // Create the window
        window = glfwCreateWindow(preferredResolution[0], preferredResolution[1], "New Window", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
//            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }
}