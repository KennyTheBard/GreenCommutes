package Control;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.*;

public class Window extends Canvas {

    public Window(int width, int height, String title, Program program) {
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(program);
        frame.setVisible(true);
        program.start();
    }
}