package FigureClass;

/**
 * Треугольник
 */
@FigureInfo(nameFigure = "Треугольник", shortNameFigure = "T", NumberOfArgs = 3)
public class Triangle implements Figure {
    private int a;
    private int b;
    private int c;

    public Triangle(int ... args) {
        if (args.length < 4) {
            this.a = args[0];
            this.b = args[1];
            this.c = args[2];
        } else throw new ArrayIndexOutOfBoundsException("Для конструктора необходимо 3 параметра!");
    }

    @Override
    @FigureMethod(methodName = "Площадь")
    public int square() {
        int p = (a + b + c) / 2;
        return (int) Math.sqrt((p*(p-a)*(p-b)*(p-c)));
    }

    @Override
    @FigureMethod(methodName = "Периметр")
    public int perimeter() {
        return a + b + c;
    }


    /**
     * Проверяем введеные стороны на возможность построения из них треугольника
     *
     * @param a сторона а
     * @param b сторона б
     * @param c сторона с
     * @return тру, если это треугольник, фалс в других случаях
     */
    public static boolean checkTriangle(int a, int b, int c){
        return a < b + c && a > b - c &&  b < a + c && b > a - c && c < a + b && c > a - b;
    }
}
