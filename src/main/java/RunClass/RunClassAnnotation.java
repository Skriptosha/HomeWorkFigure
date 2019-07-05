package RunClass;

import Annotation.FigureAdditionalMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RunClassAnnotation extends Main {
    private final String BAD_RETURN_TYPE = "Метод должен иметь возвращаемый тип boolean или void";

    /**
     * Основной метод для вывода в консоль (расширяет класс Main)
     * Выбор фигуры происходит через аннотации и рефлексию
     * Если класс содержит метод, отмеченный аннотацией FigureAdditionalMethod он так же будет обработан
     * есть два варианта обработки - с блокированием дальнейшего выполнения, попадает в массив blockMethods,
     * пока не будет выполнено условие, или без блокирования, попадает в массив voidMethods,
     * (зависит от типа возвращаемого значения метода - для блокировки нужен boolean)
     * Сначала выполняются методы с блокировкой, потом методы без блокировки
     *
     * @throws ClassNotFoundException кинет из help() -> Main.getFigureAnnotation -> Main.getFigureClass
     */
    void run() throws ClassNotFoundException {
        Class<? extends Annotation> AnnoForRunMethod = FigureAdditionalMethod.class;
        String s;
        do {
            help();
            s = scanner.next().toUpperCase();
            try {
                out:
                if (getFigureClasses().containsKey(s)) {
                    int[] param = null;

                    // фильтруем массив по методам с возвращаемым типом boolean
                    Method[] blockMethods = Arrays.stream(getFigureClasses().get(s).getDeclaredMethods())
                            .filter(m -> (m.isAnnotationPresent(AnnoForRunMethod)
                                    && m.getReturnType().equals(boolean.class)))
                            .toArray(Method[]::new);

                    for (Method m : blockMethods) {
                        do {
                            param = construct(getFigureClasses().get(s));
                            if (param == null) break out;
                        } while (!runAdditionalMethod(s, m, param));
                    }

                    // фильтруем массив по методам с возвращаемым типом void
                    Method[] voidMethods = Arrays.stream(getFigureClasses().get(s).getDeclaredMethods())
                            .filter(m -> (m.isAnnotationPresent(AnnoForRunMethod)
                                    && m.getReturnType().equals(void.class)))
                            .toArray(Method[]::new);

                    for (Method m : voidMethods) {
                        param = param == null ? construct(getFigureClasses().get(s)) : param;
                        if (param == null) break out;
                        runAdditionalMethod(s, m, param);
                    }

                    //запускаем метод по выполнению методом с аннотацией @FigureMainMethod
                    calculate(getFigureClasses().get(s).getDeclaredConstructor(int[].class)
                            .newInstance(param == null ? construct(getFigureClasses().get(s)) : param));
                }
            } catch (InstantiationException | InvocationTargetException
                    | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }

    /**
     * Запуск метода с аннотацией @FigureAdditionalMethod, происходит через invoke.
     * Метод может либо принимать входящие параметры, либо быть без параметров,
     * если метод типа void - runAdditionalMethod вернет false и в параметре int[] param
     * можно отправлять все что угодно, входящие параметры проверяются через getParameterCount()
     *
     * Не работает прием null в методе .invoke через оператор ? :, поэтому пришлось разделить.
     *
     * @param s ключ для HashMap FigureClasses
     * @param m метод который надо вызвать
     * @param param список входящих параметров int[] для метода m
     * @return если метод m возвращает boolean, то вернет то же, что и метод m,
     * если метод m void - то false
     *
     */
    private boolean runAdditionalMethod(String s, Method m, int[] param)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object ob;
        // если метод принимает какие-то параметры
        if (m.getParameterCount() != 0 ) {
             ob = getFigureClasses().get(s).getMethod(m.getName(),
                    m.getParameterCount() == 0 ? null : m.getParameterTypes())
                    .invoke(getFigureClasses().get(s),
                            param);
        } else {
            //если метод не принимает ничего
            ob = getFigureClasses().get(s).getMethod(m.getName())
                    .invoke(getFigureClasses().get(s));
        }
        return ob != null && (boolean) ob;
    }


}
