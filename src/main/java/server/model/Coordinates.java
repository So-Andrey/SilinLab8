package server.model;

/**
 * The type Coordinates.
 */
public class Coordinates {
    private int x;
    private int y; //Значение поля должно быть больше -775, Поле не может быть null

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates(int x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Integer getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}