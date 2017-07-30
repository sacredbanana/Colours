package net.minotaurcreative;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class ColourScreen extends JPanel {
    private final int TOTAL_COLOURS = 32768;
    private final int NUM_ALGORITHMS = 5;
    private int currentAlgorithm = 0;
    private int cycle = 0;
    private int cycleSpeed = 0;
    private ArrayList<PositionedColour> colours;
    private boolean blueGradientUp = true;
    private boolean greenGradientUp = true;

    public ColourScreen() {
        colours = new ArrayList<>(TOTAL_COLOURS);
        standard();
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

        for (PositionedColour colour : colours) {
            int coordX = colour.xPos;
            int coordY = colour.yPos;
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
            int blockWidth = 5;//getWidth() / 256;
            int blockHeight = 6;// getHeight() / 128;// BLOCK_SIZE;
            g.fillRect(colour.xPos * blockWidth, colour.yPos * blockHeight,blockWidth, blockHeight);
        }
    }

    private void standard() {
        int xCoord = 0;
        int yCoord = 0;
        for (int red = 7; red <= 255; red += 8) {
            for (int green = 7; green <= 255; green += 8) {
                for (int blue = 7; blue <= 255; blue += 8) {
                    if ((++xCoord) >= 256) {
                        xCoord = 0;
                        yCoord++;
                    }
                    PositionedColour colour = new PositionedColour(red, green, blue, xCoord, yCoord);
                    colours.add(colour);
                }
            }
        }
    }

    private void smooth() {
        int xCoord = 0;
        int yCoord = 0;
        for (int red = 7; red <= 255; red += 8) {
            if (greenGradientUp) {
                for (int green = 7; green <= 255; green += 8) {
                    if (blueGradientUp) {
                        for (int blue = 7; blue <= 255; blue += 8) {
                            if ((++xCoord) >= 256) {
                                xCoord = 0;
                                yCoord++;
                            }
                            PositionedColour colour = new PositionedColour(red, green, blue, xCoord, yCoord);
                            colours.add(colour);
                        }
                    } else {
                        for (int blue = 255; blue >= 7; blue -= 8) {
                            if ((++xCoord) >= 256) {
                                xCoord = 0;
                                yCoord++;
                            }
                            PositionedColour colour = new PositionedColour(red, green, blue, xCoord, yCoord);
                            colours.add(colour);
                        }
                    }
                    blueGradientUp = !blueGradientUp;
                }
            } else {
                for (int green = 255; green >= 7; green -= 8) {
                    if (blueGradientUp) {
                        for (int blue = 7; blue <= 255; blue += 8) {
                            if ((++xCoord) >= 256) {
                                xCoord = 0;
                                yCoord++;
                            }
                            PositionedColour colour = new PositionedColour(red, green, blue, xCoord, yCoord);
                            colours.add(colour);
                        }
                    } else {
                        for (int blue = 255; blue >= 7; blue -= 8) {
                            if ((++xCoord) >= 256) {
                                xCoord = 0;
                                yCoord++;
                            }
                            PositionedColour colour = new PositionedColour(red, green, blue, xCoord, yCoord);
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
            PositionedColour colour = colours.get(i);
            int randomIndex = randomGenerator.nextInt(colours.size());
            int xPosSwapped = colours.get(randomIndex).xPos;
            int yPosSwapped = colours.get(randomIndex).yPos;
            colours.get(randomIndex).xPos = colour.xPos;
            colours.get(randomIndex).yPos = colour.yPos;
            colours.get(i).xPos = xPosSwapped;
            colours.get(i).yPos = yPosSwapped;
        }
    }

    private void clumpColours() {
        standard();
        
        Collections.sort(colours, (colour2, colour1) -> {
            if (colour1.getRed() < colour2.getRed())
                return -1;
            if (colour1.getRed() == colour2.getRed())
                return 0;
            return 1;
        });

        Iterator<PositionedColour> iterator = colours.iterator();
        for (int y = 0; y < 128; y++) {
            for (int x = 0; x < 256; x++) {
                PositionedColour colour = iterator.next();
                colour.xPos = x;
                colour.yPos = y;
            }
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

    public void switchAlgorithm() {
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
                clumpColours();
                break;
            case 4:
                standard();
                clumpColours();
                break;
        }
    }
}