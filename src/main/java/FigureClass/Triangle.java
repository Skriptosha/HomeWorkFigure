package FigureClass;

import Annotation.FigureAdditionalMethod;
import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;

/**
 * Треугольник
 */
@FigureInfo(figureName = "Треугольник", figureShortName = "T", NumberOfArgs = 3)
public class Triangle implements Figure {

    @FigureFieldInfo(fieldName = "Сторона а треугольника")
    private int a;
    @FigureFieldInfo(fieldName = "Сторона b треугольника")
    private int b;
    @FigureFieldInfo(fieldName = "Сторона c треугольника")
    private int c;

    public Triangle(int ... args) {
        if (args.length > 3) {
            this.a = args[0];
            this.b = args[1];
            this.c = args[2];
        } else throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
    }

    @Override
    public int square() {
        int p = (a + b + c) / 2;
        return (int) Math.sqrt((p*(p-a)*(p-b)*(p-c)));
    }

    @Override
    public int perimeter() {
        return a + b + c;
    }


    /**
     * Проверяем введеные стороны на возможность построения из них треугольника
     *
     * @param args int[] параметров треугольника
     * @return тру, если это треугольник, фалс в других случаях
     */

    @FigureAdditionalMethod(methodName = "checkTriangle", argsClass = int[].class, isBlock = true)
    public static boolean checkTriangle(int ... args){
        if (args.length < 4) throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
        return args[0] < args[1] + args[2] && args[0] > args[1] - args[2] && args[1] < args[0] + args[2]
                && args[1] > args[0] - args[2] && args[2] < args[0] + args[1] && args[2] > args[0] - args[1];
    }
}
