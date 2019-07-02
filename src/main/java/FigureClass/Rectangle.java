package FigureClass;


import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;

/**
 * Прямоугольник
 */
@FigureInfo(figureName = "Прямоугольник", figureShortName = "R", NumberOfArgs = 2)
public class Rectangle implements Figure {

    @FigureFieldInfo(fieldName = "Длина прямоугольника")
    private int a;

    @FigureFieldInfo(fieldName = "Ширина прямоугольника")
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
