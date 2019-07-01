package FigureClass;

/**
 * Треугольник
 */
public class Triangle implements Figure {
    private int a;
    private int b;
    private int c;

    public Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
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
     * @param a сторона а
     * @param b сторона б
     * @param c сторона с
     * @return тру, если это треугольник, фалс в других случаях
     */
    public static boolean checkTriangle(int a, int b, int c){
        return a < b + c && a > b - c &&  b < a + c && b > a - c && c < a + b && c > a - b;
    }
}
