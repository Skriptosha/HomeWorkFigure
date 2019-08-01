package figureclass;


import annotation.FigureAdditionalMethod;
import annotation.FigureFieldInfo;
import annotation.FigureInfo;
import annotation.FigureMainMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static runclass.RunClassSpring.getParam;

/**
 * Прямоугольник
 */
@Component
@FigureInfo(figureName = "Прямоугольник", figureShortName = "R", NumberOfArgs = 2)
public class Rectangle implements Figure {

    @FigureFieldInfo(fieldName = "длина прямоугольника")
    private int a;

    @FigureFieldInfo(fieldName = "ширина прямоугольника")
    private int b;

    @Autowired
    public Rectangle(){
        setParams(getParam());
    }

    public Rectangle(int ... args) {
        setParams(args);
    }

    @Override
    public void setParams(int... args) {
        if (args.length < 3) {
            this.a = args[0];
            this.b = args[1];
        } else throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 2 параметра!");
    }

    @Override
    @FigureMainMethod(methodName = "Площади", methodShortName = "S")
    public int square() {
        return a*b;
    }

    @Override
    @FigureMainMethod(methodName = "Периметра", methodShortName = "P")
    public int perimeter() {
        return 2*(a + b);
    }



    @FigureAdditionalMethod(methodName = "isSquare")
    public static void isSquare(int ... args){
        if (args.length < 2) throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
        if (args[0] == args[1]) System.out.println("Стороны равны, данная фигура является квадратом!");
    }
}
