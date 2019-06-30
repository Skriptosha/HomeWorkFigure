import FigureClass.*;

import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {
    private static final String Triangle = "T";
    private static final String Square = "S";
    private static final String Rectangle = "R";
    private static final String Circle = "C";
    private static final String EXIT = "Q";
    private static String s;
    private static Scanner scanner;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        int[] param;
        scanner = new Scanner(System.in);
        Figure figure;
        do {
            help();
            s = scanner.next();
            switch (s.toUpperCase()) {
                case Triangle: {
                    param = construct(Triangle.class);
                    figure = new Triangle(param[0], param[1], param[2]);
                    calculate(figure);
                    break;
                }
                case Square: {
                    param = construct(Square.class);
                    figure = new Square(param[0]);
                    calculate(figure);
                    break;
                }
                case Rectangle: {
                    param = construct(Rectangle.class);
                    figure = new Rectangle(param[0], param[1]);
                    calculate(figure);
                    break;
                }
                case Circle: {
                    param = construct(Circle.class);
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
        int[] param;
        int c = 0;
        String pattern = "\\d*";
        System.out.println("Введите стороны для фигуры: " + figure.getSimpleName());
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
            if (s.equalsIgnoreCase("S")) System.out.println("Площадь фигуры равна: " + figure.square());
            if (s.equalsIgnoreCase("P")) System.out.println("Периметр фигуры равен: " + figure.perimeter());
        } while (!s.equalsIgnoreCase(EXIT));
    }

    private void help2() {
        System.out.println("Для вычисления площади нажмите S: " + "\n" +
                "Для вычисления площади нажмите P: " + "\n" +
                "Для возврата в предыдущее меню нажмите \"" + EXIT + "\"");
    }
}

