import FigureClass.*;

import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {
    private static final String Triangle = "T";
    private static final String Square = "S";
    private static final String Rectangle = "R";
    private static final String Circle = "C";
    private static final String EXIT = "Q";
    private static final String Area = "S";
    private static final String Perimeter = "P";
    private static final String Text1 = "Не соблюдается правило треугольника: Любая сторона треугольника " +
            "меньше суммы двух других сторон и больше\n" +
            "их разности";
    private static final String TextExit = "Для возврата в предыдущее меню нажмите \"" + EXIT + "\"";
    private static Scanner scanner;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        int[] param;
        scanner = new Scanner(System.in);
        Figure figure;
        String s;
        do {
            help();
            s = scanner.next();
            out : switch (s.toUpperCase()) {
                case Triangle: {
                    do {
                        if ((param = construct(Triangle.class)) == null) break out;
                        if (!FigureClass.Triangle.checkTriangle(param[0], param[1], param[2]))
                            System.out.println(Text1);
                        else break;
                    } while (true);
                    figure = new Triangle(param[0], param[1], param[2]);
                    calculate(figure);
                    break;
                }
                case Square: {
                    if ((param = construct(Square.class)) == null) break;
                    figure = new Square(param[0]);
                    calculate(figure);
                    break;
                }
                case Rectangle: {
                    if ((param = construct(Rectangle.class)) == null) break;
                    figure = new Rectangle(param[0], param[1]);
                    calculate(figure);
                    break;
                }
                case Circle: {
                    if ((param = construct(Circle.class)) == null) break;
                    figure = new Circle(param[0]);
                    calculate(figure);
                    break;
                }
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }

    private void help() {
        System.out.println("Выбирите фигуру: " + "\n" +
                "Треуголькик: \"" + Triangle + "\"" + "\n" +
                "Квадрат: \"" + Square + "\"" + "\n" +
                "Прямоугольник \"" + Rectangle + "\"" + "\n" +
                "Круг \"" + Circle + "\"" + "\n" +
                "Для выхода нажмите \"" + EXIT + "\"");
    }

    private int[] construct(Class figure) {
        String s;
        int[] param;
        int c = 0;
        String pattern = "\\d*";
        System.out.println("Введите стороны для фигуры: " + figure.getSimpleName());
        System.out.println(TextExit);
        Field[] fields = figure.getDeclaredFields();
        param = new int[fields.length];
        for (Field cl : fields) {
            while (true) {
                System.out.println("Введите значение для стороны \"" + cl.getName() + "\" : ");
                s = scanner.next();
                if (s.matches(pattern)) {
                    param[c] = Integer.parseInt(s);
                    break;
                }
                if (s.equalsIgnoreCase("Q")) {
                    return null;
                }
            }
            c++;
        }
        return param;
    }

    private void calculate(Figure figure) {
        String s;
        do {
            help2();
            s = scanner.next();
            if (s.equalsIgnoreCase(Area)) System.out.println("Площадь фигуры равна: " + figure.square());
            if (s.equalsIgnoreCase(Perimeter)) System.out.println("Периметр фигуры равен: " + figure.perimeter());
        } while (!s.equalsIgnoreCase(EXIT));
    }

    private void help2() {
        System.out.println("Для вычисления площади нажмите " + Area + " : " + "\n" +
                "Для вычисления площади нажмите " + Perimeter + " : " + "\n" +
                TextExit);
    }
}

