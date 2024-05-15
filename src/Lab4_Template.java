import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Lab4_Template {  // Replace "Lab4_Template" with your file name

    // Variables that will be updated
    private long window;  // The window handle

    private double speed = 0.01;  // Control the speed
    private double xScale = 1;
    private double yScale = 1;

    // Fixed value constants
    final private double gap = 1;

    public static void main(String[] args) {
        new Lab4_Template().run();  // Replace "Lab4_Template" with your file name
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();


        glScaled(0.7, 0.7, 0.7);  // Scale all shapes to fit inside our window

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            if (glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {  // if up arrow key is pressed
                yScale += speed;  // Move up by Increasing the value of yPosition
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {  // if down arrow key is pressed
                yScale -= speed;  // Move down by decreasing the value of yPosition
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Duplicate the matrix (push its value to the matrix stack) to save the previous transformation state.
            glPushMatrix();
            {
            /* Any transformations made after this statement will not affect the other matrices i.e. the transformations
               will be applied to the shapes written between glPushMatrix() and glPopMatrix() only. */

                // Moving the drawing cursor to the right
                glTranslated(0.7, 0, 0);

                // Scale the shape in Y axis only
                glScaled(1, yScale, 1);  // Using the values of (yScale) to change the triangle's scale

                glBegin(GL_TRIANGLES);
                { // Draw a triangle

                    glColor3d(0.8, 0.6, 0.7);  // custom color
                    glVertex3f(0, 0.5f, 0);  // Top point
                    glVertex3f(-0.5f, -0.5f, 0);  // Left point
                    glVertex3f(0.5f, -0.5f, 0);  // Right point

                }
                glEnd();

            }
            glPopMatrix();  // Restore the global matrix (pop the matrix stack) and set it as the current matrix.

            // Duplicate the matrix (push its value to the matrix stack) to save the previous transformation state.
            glPushMatrix();
            {
            /* Any transformations made after this statement will not affect the other matrices i.e. the transformations
               will be applied to the shapes written between glPushMatrix() and glPopMatrix() only. */

                // Moving the drawing cursor to the left
                glTranslated(-0.7, 0, 0);

                // Scale the shape in X axis only
                glScaled(xScale, 1, 1);  // Using the values of (xScale) to change the triangle's scale

                glBegin(GL_QUADS);
                {  // Draw a quad (square or rectangle)

                    glColor3d(0, 1, 0);  // Green color
                    glVertex3f(0.5f, 0.5f, 0);  // Top right
                    glColor3d(1, 0, 0);  // Red color
                    glVertex3f(-0.5f, 0.5f, 0);  // Top left
                    glColor3d(1, 1, 1);  // White color
                    glVertex3f(-0.5f, -0.5f, 0);  // Bottom left
                    glColor3d(0, 0, 1);  // Blue color
                    glVertex3f(0.5f, -0.5f, 0);  // Bottom right

                }
                glEnd();
            }
            glPopMatrix();  // Restore the global matrix (pop the matrix stack) and set it as the current matrix.


            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
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

        // Create the window
        window = glfwCreateWindow(1000, 600, "Hello World!", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
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
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

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
        