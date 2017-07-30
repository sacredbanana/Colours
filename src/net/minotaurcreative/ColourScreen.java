package net.minotaurcreative;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ColourScreen extends JPanel {
    private final int TOTAL_COLOURS = 32768;
    private final int NUM_ALGORITHMS = 5;
    private int currentAlgorithm = 0;
    private int cycle = 0;
    private int cycleSpeed = 0;
    private ArrayList<Color> colours;
    private boolean blueGradientUp = true;
    private boolean greenGradientUp = true;

    public ColourScreen() {
        colours = new ArrayList<Color>(TOTAL_COLOURS);
        standard();
        //smooth();
        //colourCubeRun();
        //randomSpread();
        //clumpColours();
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
            int red = colour.getRed() + cycle;
            if (red > 255)
                red -= 255;
            int green = colour.getGreen() + cycle;
            if (green > 255)
                green -= 255;
            int blue = colour.getBlue() + cycle;
            if (blue > 255)
                blue -= 255;
            Color adjustedColour = new Color(red, green, blue);
            g.setColor(adjustedColour);
            int BLOCK_SIZE = getWidth() * getHeight() / TOTAL_COLOURS;
            int blockWidth = 5;//getWidth() / 256;
            int blockHeight = 6;// getHeight() / 128;// BLOCK_SIZE;
            g.fillRect(coordX * blockWidth, coordY * blockHeight,blockWidth, blockHeight);
            if ((++coordX * blockWidth) >= 1280) {
                coordX = 0;
                coordY++;
            }
        }
    }

    private void standard() {
        for (int red = 7; red <= 255; red += 8) {
            for (int green = 7; green <= 255; green += 8) {
                for (int blue = 7; blue <= 255; blue += 8) {
                    Color colour = new Color(red, green, blue);
                    colours.add(colour);
                }
            }
        }
    }

    private void smooth() {
        for (int red = 7; red <= 255; red += 8) {
            if (greenGradientUp) {
                for (int green = 7; green <= 255; green += 8) {
                    if (blueGradientUp) {
                        for (int blue = 7; blue <= 255; blue += 8) {
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    } else {
                        for (int blue = 255; blue >= 7; blue -= 8) {
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            } else {
                for (int green = 255; green >= 7; green -= 8) {
                    if (blueGradientUp) {
                        for (int blue = 7; blue <= 255; blue += 8) {
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    } else {
                        for (int blue = 255; blue >= 7; blue -= 8) {
                            Color colour = new Color(red, green, blue);
                            colours.add(colour);
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            }
            greenGradientUp = !greenGradientUp;
        }
        System.out.println(colours.size());
    }

    private void randomSpread() {
        standard();
        Random randomGenerator = new Random();
        for (int i = 0; i < colours.size(); i++) {
            Color colour = colours.get(i);
            int randomIndex = randomGenerator.nextInt(colours.size());
            Color colourSwapped = colours.get(randomIndex);
            colours.set(randomIndex, colour);
            colours.set(i, colourSwapped);
        }
    }

    private void clumpColours() {
        Collections.sort(colours, (colour2, colour1) -> {
            if (colour1.getRed() < colour2.getRed())
                return -1;
            if (colour1.getRed() == colour2.getRed())
                return 0;
            return 1;
        });

        Collections.sort(colours, (colour2, colour1) -> {
            if (colour1.getBlue() > colour2.getBlue())
                return -1;
            if (colour1.getBlue() == colour2.getBlue())
                return 0;
            return 1;
        });
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

    public void switchAlgortithm() {
        colours.clear();
        if (++currentAlgorithm >= NUM_ALGORITHMS)
            currentAlgorithm = 0;
        switch (currentAlgorithm) {
            case 0:
                standard();
                break;
            case 1:
                smooth();
                break;
            case 2:
                randomSpread();
                break;
            case 3:
                randomSpread();
                clumpColours();
                break;
            case 4:
                standard();
                clumpColours();
                break;
        }
    }
}