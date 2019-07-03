package RunClass;

import Annotation.FigureAdditionalMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class RunClassAnnotation extends Main {


    /**
     * Основной метод для вывода в консоль
     * Выбор фигуры происходит через аннотации и рефлексию
     * Если класс содержит метод, отмеченный аннотацией FigureAdditionalMethod он так же будет обработан
     * есть два варианта обработки - с блокированием дальнейшего выполнения, пока не будет выполнено условие,
     * или без блокирования
     *
     * @throws ClassNotFoundException
     */
    void run() throws ClassNotFoundException {
        scanner = new Scanner(System.in);
        String s;
        do {
            help();
            s = scanner.next().toUpperCase();
            try {
                out:
                if (getFigureClasses().containsKey(s)) {
                    int[] param = null;
                    Method[] methods = getFigureClasses().get(s).getDeclaredMethods();
                    for (Method m : methods) {
                        if (m.isAnnotationPresent(FigureAdditionalMethod.class)) {
                            FigureAdditionalMethod an = m.getDeclaredAnnotation(FigureAdditionalMethod.class);
                            do {
                                if (m.getParameterCount() > 0 && param == null)
                                    param = construct(getFigureClasses().get(s));
                                if (param == null) break out;
                                else if (an.isBlock() && (boolean) getFigureClasses().get(s).getMethod(m.getName()
                                        , an.argsClass()).invoke(getFigureClasses().get(s), param)) {
                                    param = null;
                                    break;
                                }
                                else if (!an.isBlock()) {
                                    getFigureClasses().get(s).getMethod(m.getName(), an.argsClass())
                                            .invoke(getFigureClasses().get(s), param);
                                    break;
                                }
                            } while (true);
                        }
                    }
                    calculate(getFigureClasses().get(s).getDeclaredConstructor(int[].class)
                            .newInstance(param == null ? construct(getFigureClasses().get(s)) : param));
                }
            } catch (InstantiationException | InvocationTargetException
                    | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }


}
