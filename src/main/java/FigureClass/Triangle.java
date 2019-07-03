package FigureClass;

import Annotation.FigureAdditionalMethod;
import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;
import Annotation.FigureMainMethod;

/**
 * Треугольник
 */
@FigureInfo(figureName = "Треугольник", figureShortName = "T", NumberOfArgs = 3)
public class Triangle implements Figure {

    @FigureFieldInfo(fieldName = "сторона а треугольника")
    private int a;
    @FigureFieldInfo(fieldName = "сторона b треугольника")
    private int b;
    @FigureFieldInfo(fieldName = "сторона c треугольника")
    private int c;

    private static final String textWrong = "Не соблюдается правило треугольника: Любая сторона треугольника " +
            "меньше суммы двух других сторон и больше\n" +
            "их разности";

    public Triangle(int ... args) {
        if (args.length > 3) {
            this.a = args[0];
            this.b = args[1];
            this.c = args[2];
        } else throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
    }

    @Override
    @FigureMainMethod(methodName = "Площади", methodShortName = "S")
    public int square() {
        int p = (a + b + c) / 2;
        return (int) Math.sqrt((p*(p-a)*(p-b)*(p-c)));
    }

    @Override
    @FigureMainMethod(methodName = "Периметра", methodShortName = "P")
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
        if (args.length < 3) throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
        boolean b = args[0] < args[1] + args[2] && args[0] > args[1] - args[2] && args[1] < args[0] + args[2]
                && args[1] > args[0] - args[2] && args[2] < args[0] + args[1] && args[2] > args[0] - args[1];
        if (!b) System.out.println(textWrong);
        return b;
    }

    @FigureAdditionalMethod(methodName = "isIsosceles", argsClass = int[].class, isBlock = false)
    public static void isIsosceles(int ... args) {
        if (args.length < 3) throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
        if (args[0] == args[1] || args[0] == args[2] || args[1] == args[2])
            System.out.println("Данный треугольник является равнобедренным");
    }
}
