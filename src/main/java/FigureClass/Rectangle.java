package FigureClass;


/**
 * Прямоугольник
 */
@FigureInfo(nameFigure = "Прямоугольник", shortNameFigure = "R", NumberOfArgs = 2)
public class Rectangle implements Figure {
    private int a;
    private int b;

    public Rectangle(int ... args) {
        if (args.length < 3) {
            this.a = args[0];
            this.b = args[1];
        } else throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 2 параметра!");
    }

    @Override
    public int square() {
        return a*b;
    }

    @Override
    public int perimeter() {
        return 2*(a + b);
    }
}
