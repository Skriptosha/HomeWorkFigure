package FigureClass;

/**
 * Квадрат
 */
public class Square implements Figure {
    private int a;

    public Square(int a) {
        this.a = a;
    }

    @Override
    public int square() {
        return (int) Math.pow(a, 2);
    }

    @Override
    public int perimeter() {
        return 4*a;
    }
}
