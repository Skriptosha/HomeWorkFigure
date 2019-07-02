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
            out : switch (s.toUpperCase()) {
                case T: {
                    do {
                        if ((param = construct(Triangle.class)) == null) break out;
                        if (!Triangle.checkTriangle(param[0], param[1], param[2]))
                            System.out.println(Text1);
                        else break;
                    } while (true);
                    figure = new Triangle(param[0], param[1], param[2]);
                    calculate(figure);
                    break;
                }
                case SquareClassName: {
                    if ((param = construct(Square.class)) == null) break;
                    figure = new Square(param[0]);
                    calculate(figure);
                    break;
                }
                case RectangleClassName: {
                    if ((param = construct(Rectangle.class)) == null) break;
                    figure = new Rectangle(param[0], param[1]);
                    calculate(figure);
                    break;
                }
                case CircleClassName: {
                    if ((param = construct(Circle.class)) == null) break;
                    figure = new Circle(param[0]);
                    calculate(figure);
                    break;
                }
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }




}
