package FigureClass;


import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;

/**
 * Квадрат
 */
@FigureInfo(figureName = "Квадрат", figureShortName = "S", NumberOfArgs = 1)
public class Square implements Figure {

    @FigureFieldInfo(fieldName = "Сторона квадрата")
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
