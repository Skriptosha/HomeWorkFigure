package FigureClass;


import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;
import Annotation.FigureMainMethod;

/**
 * Круг
 */
@FigureInfo(figureName = "Круг", figureShortName = "C", NumberOfArgs = 1)
public class Circle implements Figure {

    @FigureFieldInfo(fieldName = "радиус круга")
    private int r;

    public Circle(int ... args) {
        this.r = args[0];
    }

    @Override
    @FigureMainMethod(methodName = "Площади", methodShortName = "S")
    public int square() {
        return (int) (Math.PI*Math.pow(r, 2));
    }

    @Override
    @FigureMainMethod(methodName = "Периметра", methodShortName = "P")
    public int perimeter() {
        return (int) (2*Math.PI*r);
    }
}
