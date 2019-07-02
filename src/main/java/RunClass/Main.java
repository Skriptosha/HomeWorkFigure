package RunClass;

import FigureClass.Figure;
import FigureClass.FigureInfo;

import java.lang.reflect.Field;
import java.util.Scanner;

public class Main {
    static final String TriangleClassName = "Triangle";
    static final String SquareClassName = "Square";
    static final String RectangleClassName = "Rectangle";
    static final String CircleClassName = "Circle";
    static final String EXIT = "Q";
    static final String Area = "S";
    static final String Perimeter = "P";
    static final String Text1 = "Не соблюдается правило треугольника: Любая сторона треугольника " +
            "меньше суммы двух других сторон и больше\n" +
            "их разности";
    static final String TextExit = "Для возврата в предыдущее меню нажмите \"" + EXIT + "\"";
    static Scanner scanner;

    public static void main(String[] args) throws ClassNotFoundException {
        new RunClassAnnotation().run();
    }

    /**
     * Меню выбора фигуры
     */
    void help() throws ClassNotFoundException {
        System.out.println("Выбирите фигуру: " + "\n" +
                "Треуголькик: \"" + getFigureAnnotation(TriangleClassName) + "\"" + "\n" +
                "Квадрат: \"" + getFigureAnnotation(SquareClassName) + "\"" + "\n" +
                "Прямоугольник \"" + getFigureAnnotation(RectangleClassName) + "\"" + "\n" +
                "Круг \"" + getFigureAnnotation(CircleClassName) + "\"" + "\n" +
                "Для выхода нажмите \"" + EXIT + "\"");
    }

    /**
     * Меню для выбора типа вычисления
     */
    void help2() {
        System.out.println("Для вычисления площади нажмите " + Area + " : " + "\n" +
                "Для вычисления периметра нажмите " + Perimeter + " : " + "\n" +
                TextExit);
    }

    static Class<?> getFigureClass(String className) throws ClassNotFoundException {
        return Class.forName("FigureClass." + className);
    }

    static FigureInfo getFigureAnnotation(String className) throws ClassNotFoundException {
            return getFigureClass(className).getDeclaredAnnotation(FigureInfo.class);
    }

    /**
     * Метод для получения аргументов для конструкторов фигур (использует java.lang.reflect)
     *
     * @param figure Класс фигуры, реализующей интерфейс Figure
     * @return массив значений для полей класса фигуры
     */
    int[] construct(Class <? extends Figure> figure) {
        String s;
        int[] param;
        int c = 0;
        String pattern = "\\d*";
        System.out.println("Введите стороны для фигуры: "
                + figure.getDeclaredAnnotation(FigureInfo.class).nameFigure());
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

    /**
     * Метод для вычисления площади и периметра фигур
     *
     * @param figure Ссылка на обьект фигуры типа Figure
     */
    void calculate(Figure figure) {
        String s;
        do {
            help2();
            s = scanner.next();
            if (s.equalsIgnoreCase(Area)) System.out.println("Площадь фигуры равна: " + figure.square());
            if (s.equalsIgnoreCase(Perimeter)) System.out.println("Периметр фигуры равен: " + figure.perimeter());
        } while (!s.equalsIgnoreCase(EXIT));
    }
}

