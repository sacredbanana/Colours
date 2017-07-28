package net.minotaurcreative;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {
        final int SCREEN_WIDTH = 256*3;
        final int SCREEN_HEIGHT = 128*6;
        JFrame frame = new JFrame("Colours!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ColourScreen colourScreen = new ColourScreen();
        frame.add(colourScreen);
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}

            public void keyPressed(KeyEvent e) {
                System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                switch (e.getKeyCode()) {
                    case 32:
                        colourScreen.toggleSmoothGradients();
                        break;
                    case 38:
                        colourScreen.increaseCycleSpeed();
                        break;
                    case 40:
                        colourScreen.decreaseCycleSpeed();
                        break;
                }
            }

            public void keyReleased(KeyEvent e) {}
        });
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null); // Centre window on screen
        frame.setVisible(true);
    }
}
