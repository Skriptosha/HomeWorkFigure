package runclass;

import figureclass.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class RunClassSwitch extends Main {

    /**
     * Основной метод для вывода в консоль
     * Выбор фигуры происходит через оператор switch
     * Зависим от аннотаций @FigureFieldInfo и @FigureInfo в классах фигур, так как метод construct
     * является универсальным и использует их.
     */
    void run() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        int[] param;
        scanner = new Scanner(System.in);
        Figure figure;
        String s;

        do {
            help();
            s = scanner.next();
            out:
            switch (s.toUpperCase()) {
                case ("T"): {
                    do {
                        if ((param = construct(Triangle.class)) == null) break out;
                        if (Triangle.checkTriangle(param)) break;
                    } while (true);
                    Triangle.isIsosceles(param);
                    figure = new Triangle(param);
                    calculate(figure);
                    break;
                }
                case ("S"): {
                    if ((param = construct(Square.class)) == null) break;
                    figure = new Square(param);
                    calculate(figure);
                    break;
                }
                case ("R"): {
                    if ((param = construct(Rectangle.class)) == null) break;
                    Rectangle.isSquare(param);
                    figure = new Rectangle(param);
                    calculate(figure);
                    break;
                }
                case ("C"): {
                    if ((param = construct(Circle.class)) == null) break;
                    figure = new Circle(param);
                    calculate(figure);
                    break;
                }
            }

        } while (!s.equalsIgnoreCase(EXIT));
    }


}
