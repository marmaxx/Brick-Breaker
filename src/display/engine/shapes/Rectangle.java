package display.engine.shapes;

import display.engine.rules.Shape;

public class Rectangle extends Shape {
    public Rectangle(
        int posX, int posY,
        int scaleX, int scaleY,
        int red, int blue, int green
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
    }

    public Rectangle() {
        super(100, 100, 100, 100, 1, 0, 0);
    }
}
