package RunClass;

import FigureClass.*;

import java.util.Scanner;

public class RunClassSwitch extends Main {

    /**
     * Основной метод для вывода в консоль
     * Выбор фигуры происходит через оператор switch
     */
    void run() throws ClassNotFoundException {
        int[] param;
        scanner = new Scanner(System.in);
        Figure figure;
        String s;

        do {
            help();
            s = scanner.next();
            out:
            if (s.equalsIgnoreCase(getFigureAnnotation(TriangleClassName).figureShortName())) {
                do {
                    if ((param = construct(Triangle.class)) == null) break out;
                    if (!Triangle.checkTriangle(param))
                        System.out.println(Text1);
                    else break;
                } while (true);
                figure = new Triangle(param);
                calculate(figure);
                break;
            } else if (s.equalsIgnoreCase(getFigureAnnotation(SquareClassName).figureShortName())) {
                if ((param = construct(Square.class)) == null) break;
                figure = new Square(param);
                calculate(figure);
                break;
            } else if (s.equalsIgnoreCase(getFigureAnnotation(RectangleClassName).figureShortName())) {
                if ((param = construct(Rectangle.class)) == null) break;
                figure = new Rectangle(param);
                calculate(figure);
                break;
            } else if (s.equalsIgnoreCase(getFigureAnnotation(CircleClassName).figureShortName())) {
                if ((param = construct(Circle.class)) == null) break;
                figure = new Circle(param);
                calculate(figure);
                break;
            }

        } while (!s.equalsIgnoreCase(EXIT));
    }


}
