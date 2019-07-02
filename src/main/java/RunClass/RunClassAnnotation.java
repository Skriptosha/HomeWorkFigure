package RunClass;

import Annotation.FigureAdditionalMethod;
import Annotation.FigureInfo;
import FigureClass.Figure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class RunClassAnnotation extends Main {

    void run() throws ClassNotFoundException {
        scanner = new Scanner(System.in);
        String s;
        int[] param = null;
        Method method = null;
        do {
            help();
            s = scanner.next();
            try {
                Class<? extends Figure> t = Main.getFigureClass(s).asSubclass(Figure.class);
                if (t.isAnnotationPresent(FigureInfo.class)) {
                    Method[] methods = t.getDeclaredMethods();
                    for (Method m : methods) {
                        if (m.isAnnotationPresent(FigureAdditionalMethod.class))
                            method = m;
                    }
                    if (null != method){
                        FigureAdditionalMethod an = method.getDeclaredAnnotation(FigureAdditionalMethod.class);
                       do {
                           System.out.println(method.getName());
                           if (method.getParameterCount() > 0)
                               param = construct(t);
                           if (an.isBlock() && (boolean) t.getMethod(method.getName()
                                   , an.argsClass()).invoke(t, param)) break;
                           else if (!an.isBlock()){
                               t.getMethod(method.getName(), an.argsClass())
                                       .invoke(t, param);
                               break;
                           }
                       }while (true);
                    }
                    calculate(t.getDeclaredConstructor(int[].class)
                            .newInstance(param == null ? construct(t) : param));

                }
            } catch (InstantiationException | InvocationTargetException
                    | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // nothing to do
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }
}
