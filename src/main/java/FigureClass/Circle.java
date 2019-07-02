package FigureClass;


import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;

/**
 * Круг
 */
@FigureInfo(figureName = "Круг", figureShortName = "C", NumberOfArgs = 1)
public class Circle implements Figure {

    @FigureFieldInfo(fieldName = "Радиус круга")
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
