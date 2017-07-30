package net.minotaurcreative;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class ColourScreen extends JPanel {
    private final int TOTAL_COLOURS = 32768;
    private int cycle = 0;
    private int cycleSpeed = 0;
    private ArrayList<Color> colours;
    private boolean smooth = false;
    private boolean blueGradientUp = true;
    private boolean greenGradientUp = true;

    public ColourScreen() {
        colours = new ArrayList<Color>(TOTAL_COLOURS);
        addColours();
        randomSpread();
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



        int coordX = 0;
        int coordY = 0;

        for (Color colour : colours) {
            g.setColor(colour);
            int BLOCK_SIZE = getWidth() * getHeight() / TOTAL_COLOURS;
            int blockWidth = 5;//getWidth() / 256;
            int blockHeight = 5;// getHeight() / 128;// BLOCK_SIZE;
            g.fillRect(coordX * blockWidth, coordY * blockHeight,blockWidth, blockHeight);
            if ((++coordX * blockWidth + blockWidth) > getWidth()) {
                coordX = 0;
                coordY++;
            }
        }
        //colours.clear();
    }

    private void addColours() {
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
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    } else {
                        for (int rawBlue = 255; rawBlue >= 7; rawBlue -= 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
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
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    } else {
                        for (int rawBlue = 255; rawBlue >= 7; rawBlue -= 8) {
                            int blue = rawBlue + cycle;
                            if (blue > 255)
                                blue -= 255;
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            }
            greenGradientUp = !greenGradientUp;
        }
    }

    private void randomSpread() {
        Random randomGenerator = new Random();
        for (int i = 0; i < colours.size(); i++) {
            Color colour = colours.get(i);
            int randomIndex = randomGenerator.nextInt(colours.size());
            Color colourSwapped = colours.get(randomIndex);
            colours.set(randomIndex, colour);
            colours.set(i, colourSwapped);
        }
    }

    public void increaseCycleSpeed() {
        cycleSpeed++;
    }

    public void decreaseCycleSpeed() {
        if (--cycleSpeed <=  0) {
            cycleSpeed = 0;
            cycle = 0;
        }
    }

    public void toggleSmoothGradients() {
        smooth = !smooth;
    }
}