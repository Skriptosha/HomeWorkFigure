package runclass;

import config.Configurator;
import figureclass.Circle;
import figureclass.Rectangle;
import figureclass.Square;
import figureclass.Triangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

@Component
public class RunClassSpring extends Main{

    @Autowired
    private Configurator configurator;

    void run() throws InvocationTargetException, IllegalAccessException {
        int[] param;
        scanner = new Scanner(System.in);
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
                    calculate(configurator.getBean(Triangle.class, param));
                    break;
                }
                case ("S"): {
                    if ((param = construct(Square.class)) == null) break;
                    calculate(configurator.getBean(Square.class, param));
                    break;
                }
                case ("R"): {
                    if ((param = construct(Rectangle.class)) == null) break;
                    Rectangle.isSquare(param);
                    calculate(configurator.getBean(Rectangle.class, param));
                    break;
                }
                case ("C"): {
                    if ((param = construct(Circle.class)) == null) break;
                    calculate(configurator.getBean(Circle.class, param));
                    break;
                }
            }

        } while (!s.equalsIgnoreCase(EXIT));
    }
}
