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

public class Q1{  // Replace "template_2D" with your file name
    // The window handle
    private long window;
    private double speed = 0.01;  // Control the speed
    private double xScale = 1;
    private double yScale = 1;
    private double xTranslation = 0;
    private double yTranslation = 0;
    private double zTranslation = 0;
    public static void main(String[] args) {
        new Q1().run();  // Replace "template_2D" with your file name
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
//        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        glTranslated(0, 0, 0);
        glEnable(GL_DEPTH_TEST);
        while (!glfwWindowShouldClose(window)) {

            if (glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {  // if up arrow key is pressed
                zTranslation += .02;  // Move up by Increasing the value of yPosition
            }
            if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {  // if down arrow key is pressed
                zTranslation -= .02;  // Move down by decreasing the value of yPosition
            }
            //if 'w' is pressed
            if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                yTranslation += .02;  // Move right by Increasing the value of xPosition
            }

            if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
                yTranslation -= .02;  // Move right by Increasing the value of xPosition
            }

            if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                xTranslation += .02;  // Move right by Increasing the value of xPosition
            }

            if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                xTranslation -= .02;  // Move right by Increasing the value of xPosition
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer



        //draw a 2d letter H as a polygon
            glPushMatrix();
            glRotatef(20,0,0,1);
        glBegin(GL_POLYGON);
        {
            glColor3d(1, 1, 1 );
            //TOP left
            glVertex3f(-.5f, .5f,0);
            //top right
            glVertex3f(-.45f, .5f,0);
            //bottom right
            glVertex3f(-.45f, .2f,0);
            //bottom left
            glVertex3f(-.5f, .2f,0);

        }
        glEnd();

            glPushMatrix();
            glTranslatef(.1f, 0, 0);
            glBegin(GL_POLYGON);
            {
                glColor3d(1, 1, 1 );
                //TOP Left
                glVertex3f(-.5f, .5f,0);
                //top right
                glVertex3f(-.45f, .5f,0);
                //bottom right
                glVertex3f(-.45f, .2f,0);
                //bottom left
                glVertex3f(-.5f, .2f,0);
            }
            glEnd();
            glPopMatrix();

        glBegin(GL_POLYGON);
        {
            glColor3d(1, 1, 1 );
            //TOP Left
            glVertex3f(-.4f, .4f,0);
            //top right
            glVertex3f(-.45f, .4f,0);
            //bottom right
            glVertex3f(-.45f, .38f,0);
            //bottom left
            glVertex3f(-.4f, .38f,0);
        }
        glEnd();
        glPopMatrix();

           glPushMatrix();
            glTranslated(xTranslation, yTranslation, zTranslation);

            drawCircle(0.5f, 0.0f, 0.2f, 100);
            glPopMatrix();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    private void drawCircle(float cx, float cy, float r, int num_segments) {
        glBegin(GL_TRIANGLE_FAN);
        glColor3f(1.0f, 1.0f, 1.0f); // White color
        glVertex2f(cx, cy); // Center of circle
        for (int i = 0; i <= num_segments; i++) {
            double angle = 2 * Math.PI * i / num_segments;
            float x = (float) (cx + Math.cos(angle) * r);
            float y = (float) (cy + Math.sin(angle) * r);
            glVertex2f(x, y);
        }
        glEnd();
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
        window = glfwCreateWindow(600, 600, "Hello World!", NULL, NULL);
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
