import java.awt.*;

/**
 * PositionedColour class for colours with inherent positions
 */
public class PositionedColour extends Color {
    /**
     * X position of colour
     */
    public int xPos;

    /**
     * Y position of colour
     */
    public int yPos;

    public PositionedColour(int red, int green, int blue, int xPos, int yPos) {
        super(red, green, blue);
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
