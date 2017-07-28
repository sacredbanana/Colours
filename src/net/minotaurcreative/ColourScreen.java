package net.minotaurcreative;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("SpellCheckingInspection")

class ColourScreen extends JPanel {
    private int cycle = 0;
    private int cycleSpeed = 0;
    private boolean smooth = false;
    private boolean blueGradientUp = true;
    private boolean greenGradientUp = true;

    ColourScreen() {
        Timer timer = new Timer(1, e -> {
            cycle += cycleSpeed;
            if (cycle > 255)
                cycle = 0;
            repaint();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        final int TOTAL_COLOURS = 32768;
        int coordX = 0;
        int coordY = 0;

        for (int rawRed = 7; rawRed <= 255; rawRed += 8) {
            int red = rawRed + cycle;
            if (red > 255)
                red -= 255;
            if (greenGradientUp || !smooth) {
                for (int rawGreen = 7; rawGreen <= 255; rawGreen += 8) {
                    int green = rawGreen + cycle;
                    if (green > 255)
                        green -= 255;
                    if (blueGradientUp || !smooth) {
                        for (int rawBlue = 7; rawBlue <= 255; rawBlue += 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            g.setColor(new Color(red,green,blue));
                            g.fillRect(coordX,coordY* getWidth()*getHeight()/ TOTAL_COLOURS,1, getWidth()*getHeight()/ TOTAL_COLOURS);
                            if (++coordX > getWidth()) {
                                coordX = 0;
                                coordY++;
                            }
                        }
                    } else {
                        for (int rawBlue = 255; rawBlue >= 7; rawBlue -= 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            g.setColor(new Color(red, green, blue));
                            g.fillRect(coordX, coordY * getWidth() * getHeight() / TOTAL_COLOURS, 1, getWidth() * getHeight() / TOTAL_COLOURS);
                            if (++coordX > getWidth()) {
                                coordX = 0;
                                coordY++;
                            }
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            } else {
                for (int rawGreen = 255; rawGreen >= 7; rawGreen -= 8) {
                    int green = rawGreen + cycle;
                    if (green > 255)
                        green -= 255;
                    if (blueGradientUp || !smooth) {
                        for (int rawBlue = 7; rawBlue <= 255; rawBlue += 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            g.setColor(new Color(red,green,blue));
                            g.fillRect(coordX,coordY* getWidth()*getHeight()/ TOTAL_COLOURS,1, getWidth()*getHeight()/ TOTAL_COLOURS);
                            if (++coordX > getWidth()) {
                                coordX = 0;
                                coordY++;
                            }
                        }
                    } else {
                        for (int rawBlue = 255; rawBlue >= 7; rawBlue -= 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            g.setColor(new Color(red, green, blue));
                            g.fillRect(coordX, coordY * getWidth() * getHeight() / TOTAL_COLOURS,1, getWidth() * getHeight() / TOTAL_COLOURS);
                            if (++coordX > getWidth()) {
                                coordX = 0;
                                coordY++;
                            }
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            }
            greenGradientUp = !greenGradientUp;
        }
    }

    void increaseCycleSpeed() {
        cycleSpeed++;
    }

    void decreaseCycleSpeed() {
        if (--cycleSpeed <=  0) {
            cycleSpeed = 0;
            cycle = 0;
        }
    }

    void toggleSmoothGradients() {
        smooth = !smooth;
    }
}