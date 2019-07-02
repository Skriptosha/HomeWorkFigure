package FigureClass;


/**
 * Квадрат
 */
@FigureInfo(nameFigure = "Квадрат", shortNameFigure = "S", NumberOfArgs = 1)
public class Square implements Figure {
    private int a;

    public Square(int ... args) {
        this.a = args[0];
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
