package FigureClass;


import Annotation.FigureAdditionalMethod;
import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;
import Annotation.FigureMainMethod;

/**
 * Квадрат
 */
@FigureInfo(figureName = "Квадрат", figureShortName = "S", NumberOfArgs = 1)
public class Square implements Figure {

    @FigureFieldInfo(fieldName = "сторона квадрата")
    private int a;

    public Square(int ... args) {
        this.a = args[0];
    }

    @Override
    @FigureMainMethod(methodName = "Площади", methodShortName = "S")
    public int square() {
        return (int) Math.pow(a, 2);
    }

    @Override
    @FigureMainMethod(methodName = "Периметра", methodShortName = "P")
    public int perimeter() {
        return 4*a;
    }

    @FigureAdditionalMethod(methodName = "test")
    public static void test(){
        System.out.println("this test");
    }
}
