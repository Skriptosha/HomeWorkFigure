package FigureClass;


/**
 * Круг
 */
@FigureInfo(nameFigure = "Круг", shortNameFigure = "C", NumberOfArgs = 1)
public class Circle implements Figure {
    private int r;

    public Circle(int ... args) {
        this.r = args[0];
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
