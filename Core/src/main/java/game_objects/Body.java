package game_objects;

public class Body {
    /**
     * Trieda ktorá definuje telo herneho objektu
     */
    private float height;
    private float width;

    public Body(float height, float width) {
        this.height = height;
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) { this.height = height; }

    public void setWidth(float width) { this.width = width; }
}
