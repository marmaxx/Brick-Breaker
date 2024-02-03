package display.engine.rules;

public abstract class GraphicalObject {

    protected int posX, posY;
    protected int scaleX, scaleY;
    protected float red, blue, green;
    
    public GraphicalObject(
        int posX, int posY,
        int scaleX, int scaleY,
        float red, float blue, float green
    ) {
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosy(int posY) {
        this.posY = posY;
    }

    public int getScaleX() {
        return scaleY;
    }

    public void setScaleX(int scaleX) {
        this.scaleX = scaleX;
    }

    public int getScaleY() {
        return scaleY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }

    public float getRed() {
        return this.red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getBlue() {
        return this.blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getGreen() {
        return this.green;
    }

    public void setGreen(float green) {
        this.green = green;
    }
}