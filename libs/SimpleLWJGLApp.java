import org.lwjgl.glfw.glfw;
import org.lwjgl.opengl.gl;
import org.lwjgl.opengl.gl11;

public class simplelwjglapp {

    private long window;

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        // initialize glfw
        if (!glfw.glfwinit()) {
            throw new illegalstateexception("unable to initialize glfw");
        }

        // create a window
        window = glfw.glfwcreatewindow(800, 600, "simple lwjgl app", 0, 0);
        if (window == 0) {
            throw new illegalstateexception("failed to create window");
        }

        // make the opengl context current
        glfw.glfwmakecontextcurrent(window);
        // enable v-sync
        glfw.glfwswapinterval(1);

        // make the window visible
        glfw.glfwshowwindow(window);

        // setup opengl
        gl.createcapabilities();
        gl11.glclearcolor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private void loop() {
        while (!glfw.glfwwindowshouldclose(window)) {
            gl11.glclear(gl11.gl_color_buffer_bit);

            // rendering code goes here

            glfw.glfwswapbuffers(window);
            glfw.glfwpollevents();
        }
    }

    private void cleanup() {
        // cleanup glfw
        glfw.glfwterminate();
    }

    public static void main(string[] args) {
        new simplelwjglapp().run();
    }
}
