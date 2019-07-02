package RunClass;

import FigureClass.Figure;
import FigureClass.FigureInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class RunClassAnnotation extends Main {

    void run() throws ClassNotFoundException {
        scanner = new Scanner(System.in);
        String s;
        do {
            help();
            s = scanner.next();
            try {
                Class<? extends Figure> t = Main.getFigureClass(s).asSubclass(Figure.class);
                if (t.isAnnotationPresent(FigureInfo.class)) {
                    calculate(t.getDeclaredConstructor(int[].class).newInstance(construct(t)));

                }
            } catch (InstantiationException | InvocationTargetException
                    | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                // nothing
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }
}
