package net.minotaurcreative;

import java.awt.*;

public class PositionedColour extends Color {
    public int xPos;
    public int yPos;

    public PositionedColour(int red, int green, int blue, int xPos, int yPos) {
        super(red, green, blue);
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
