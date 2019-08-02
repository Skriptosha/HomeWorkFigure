package runclass;

import annotation.FigureAdditionalMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     */
    void run() {
        Class<? extends Annotation> AnnoForRunMethod = FigureAdditionalMethod.class;
        String s;
        do {
            help();
            s = scanner.next().toUpperCase();
            try {
                out:
                if (getFigureClasses().containsKey(s)) {
                    int[] param = null;
                    Class<?> clazz = getFigureClasses().get(s);
                    // фильтруем массив по методам с возвращаемым типом boolean
                    List<Method> blockMethods = Arrays.stream(clazz.getDeclaredMethods())
                            .filter(m -> (m.isAnnotationPresent(AnnoForRunMethod)
                                    && m.getReturnType().equals(boolean.class)))
                            .collect(Collectors.toList());

                    for (Method m : blockMethods) {
                        do {
                            param = construct(clazz);
                            if (param == null) break out;
                        } while (!runAdditionalMethod(clazz, m, param));
                    }

                    // фильтруем массив по методам с возвращаемым типом void
                    List<Method> voidMethods = Arrays.stream(clazz.getDeclaredMethods())
                            .filter(m -> (m.isAnnotationPresent(AnnoForRunMethod)
                                    && m.getReturnType().equals(void.class)))
                            .collect(Collectors.toList());

                    for (Method m : voidMethods) {
                        param = param == null ? construct(clazz) : param;
                        if (param == null) break out;
                        runAdditionalMethodVoid(clazz, m, param);
                    }

                    //запускаем метод по выполнению методов с аннотацией @FigureMainMethod
                    calculate(clazz.getDeclaredConstructor(int[].class)
                            .newInstance(param == null ? construct(clazz) : param));
                }
            } catch (InstantiationException | InvocationTargetException
                    | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        } while (!s.equalsIgnoreCase(EXIT));
    }

    /**
     * Запуск метода с аннотацией @FigureAdditionalMethod, происходит через invoke.
     * Версия для метода @FigureAdditionalMethod с вхордящими параметрами типа int[] param
     * и типом возвращаемого значения boolean.
     * Если метод не принимает параметров, int[] param должен быть равен null
     *
     * @param clazz Class для обьекта фигуры который используем (из мапы FigureClasses)
     * @param m метод который надо вызвать
     * @param param список входящих параметров int[] для метода m
     * @return тип boolean, вернет то же, что и метод m
     *
     */
    private boolean runAdditionalMethod(Class<?> clazz, Method m, int[] param)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (boolean) clazz.getMethod(m.getName(), m.getParameterCount() == 0 ? null : m.getParameterTypes())
                    .invoke(clazz, m.getParameterCount() == 0 ? null : param);
    }

    /**
     * Версия для метода @FigureAdditionalMethod с вхордящими параметрами типа int[] param
     * и типом возвращаемого значения void. (ничего не возвращает)
     * Если метод не принимает параметров, int[] param должен быть равен null
     *
     * @param clazz clazz Class для обьекта фигуры который используем (из мапы FigureClasses)
     * @param m метод который надо вызвать
     * @param param список входящих параметров int[] для метода m
     */
    private void runAdditionalMethodVoid(Class<?> clazz, Method m, int[] param)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        clazz.getMethod(m.getName(), m.getParameterCount() == 0 ? null : m.getParameterTypes())
                .invoke(clazz, m.getParameterCount() == 0 ? null : param);
        }
}
