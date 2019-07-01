package FigureClass;

/**
 * Круг
 */
public class Circle implements Figure {
    private int r;

    public Circle(int r) {
        this.r = r;
    }

    @Override
    public int square() {
        return (int) (Math.PI*Math.pow(r, 2));
    }

    @Override
    public int perimeter() {
        return (int) (2*Math.PI*r);
    }
}
