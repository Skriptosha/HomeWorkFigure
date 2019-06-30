package FigureClass;

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
}
