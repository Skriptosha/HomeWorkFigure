package figureclass;


import annotation.FigureAdditionalMethod;
import annotation.FigureFieldInfo;
import annotation.FigureInfo;
import annotation.FigureMainMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Квадрат
 */
@FigureInfo(figureName = "Квадрат", figureShortName = "S", NumberOfArgs = 1)
@Component
public class Square implements Figure {

    @FigureFieldInfo(fieldName = "сторона квадрата")
    private int a;

    @Autowired
    public Square(){}

    public Square(int... args) {
        setParams(args);
    }

    @Override
    public void setParams(int... args) {
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
        return 4 * a;
    }



    @FigureAdditionalMethod(methodName = "test")
    public static void test() {
        System.out.println("this test");
    }
}