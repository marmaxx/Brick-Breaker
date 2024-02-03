package display.engine.rules;

public abstract class Shape extends GraphicalObject {

    public Shape(
        int posX, int posY,
        int scaleX, int scaleY,
        float red, float blue, float green
    ) {
        super(posX, posY, scaleX, scaleY, red, blue, green);
    }

}
