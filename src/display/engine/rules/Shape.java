package display.engine.rules;

public abstract class Shape extends GraphicalObject {

    public Shape(
        int posX, int posY,
        int scaleX, int scaleY,
        int red, int blue, int green
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
    }

}
