package RunClass;

import Annotation.FigureFieldInfo;
import Annotation.FigureInfo;
import Annotation.FigureMainMethod;
import FigureClass.Figure;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static HashMap<String, Class<? extends Figure>> figureClasses = new HashMap<>();
    private static final String PACKAGE = Figure.class.getPackage().toString().split(" ")[1];
    private static final String REGEX = ".java";
    private static final String FIGURE_CLASS_PATH = "src\\main\\java\\" + PACKAGE;
    static final String EXIT = "Q";
    private static final String TEXT_EXIT = "Для возврата в предыдущее меню нажмите \"" + EXIT + "\"";

    /**
     * Геттер для HashMap figureClasses.
     *
     * @return HashMap figureClasses;
     */
    static HashMap<String, Class<? extends Figure>> getFigureClasses() {
        return figureClasses;
    }

    /**
     * Статический блок для получения списка классов по пути FIGURE_CLASS_PATH.
     * При получении береться список файлов из пакета FIGURE_CLASS_PATH - фильтруются файлы с расширением REGEX,
     * далее отбираются классы совместимые с Figure.class, имеющие аннотацию FigureInfo,
     * методы которых имеют аннотацию FigureMainMethod, и поля с аннотацию FigureFieldInfo.
     * Эти классы уже попадают в HashMap figureClasses, где ключом является поле figureShortName()
     * аннотации FigureInfo, а значением класс типа Class<? extends Figure>
     */
    static {
        try {
            // берем только файлы с расширением REGEX
            Path[] paths = Files.list(Paths.get(FIGURE_CLASS_PATH)).
                    filter(p -> p.getFileName().toString().endsWith(REGEX)).toArray(Path[]::new);
            for (Path path : paths) {
                // проверяем что нужный класс найден, и реализует интерфейс Figure
                if (Figure.class.isAssignableFrom(getFigureClass(path.getFileName().toString().split(REGEX)[0]))) {
                    Class<? extends Figure> clazz = getFigureClass(path.getFileName().toString().split(REGEX)[0])
                            .asSubclass(Figure.class);
                    // проверяем наличие необходимых аннотаций
                    if (clazz.isAnnotationPresent(FigureInfo.class)
                            && isHaveAnnotation(clazz.getDeclaredMethods(), FigureMainMethod.class)
                            && isHaveAnnotation(clazz.getDeclaredFields(), FigureFieldInfo.class)) {
                        figureClasses.put(clazz.getDeclaredAnnotation(FigureInfo.class).figureShortName(), clazz);
                    }
                }
            }
            System.out.println(figureClasses.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Перегруженный метод для проверки наличия аннотации у метода
     *
     * @param methods массив методов для проверки
     * @param annotationClass аннотация, которую надо проверить
     * @return тру, если найдена хотя бы 1 аннотация annotationClass
     */
    private static boolean isHaveAnnotation(Method[] methods, Class<? extends Annotation> annotationClass) {
        int count = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(annotationClass)) count++;
        }
        return count > 0;
    }

    /**
     * Перегруженный метод для проверки наличия аннотации у поля класса
     *
     * @param fields массив полей класса для проверки
     * @param annotationClass аннотация, которую надо проверить
     * @return тру, если найдена хотя бы 1 аннотация annotationClass
     */
    private static boolean isHaveAnnotation(Field[] fields, Class<? extends Annotation> annotationClass) {
        int count = 0;
        for (Field f : fields) {
            if (f.isAnnotationPresent(annotationClass)) count++;
        }
        return count > 0;
    }

    static Scanner scanner;

    /**
     * Запуск программы, метод main
     *
     * @param args аргументы коммандной строки (не учитываются)
     * @throws ClassNotFoundException улетает в никуда
     */
    public static void main(String[] args) throws ClassNotFoundException {
        new RunClassAnnotation().run();
    }

    /**
     * Меню выбора фигуры
     * Строка формируется согласно колличеству классов фигур, полученных в статическом блоке класса Main -
     * HashMap figureClasses.
     * Далее просто выводится поле figureName() аннотации FigureInfo и поле figureShortName()
     * (оно же является ключом в figureClasses)
     */
    void help() throws ClassNotFoundException {
        System.out.println("Выбирите фигуру: ");
        for (Map.Entry<String, Class<? extends Figure>> entry : figureClasses.entrySet()) {
            System.out.printf("Для выбора фигуры \"%s\" нажмите \"%s\" \n",
                    getFigureAnnotation(entry.getValue().getSimpleName()).figureName(), entry.getKey());
        }
        System.out.println("Для выхода нажмите " + EXIT);
    }

    /**
     * Поиск класса типа Class<?> по названию класса
     * Применяется для поиска классов фигур
     *
     * @param className название класса, должен находиться в пакете FigureClass
     * @return класс Class<?> из пакета FigureClass
     * @throws ClassNotFoundException ошибка в случае, если необходимый класс отстуствует в пакете
     *                                FigureClass или ошибка в названии класса.
     */
    static Class<?> getFigureClass(String className) throws ClassNotFoundException {
        return Class.forName(PACKAGE + "." + className);
    }

    /**
     * Получение аннотации FigureInfo класса из пакета FigureClass.
     * Использует метод getFigureClass.
     *
     * @param className название класса, должен находиться в пакете FigureClass
     * @return аннотация FigureInfo класса className из пакета FigureClass
     * @throws ClassNotFoundException ошибка в случае, если необходимый класс отстуствует в пакете
     *                                FigureClass или ошибка в названии класса.
     */
    static FigureInfo getFigureAnnotation(String className) throws ClassNotFoundException {
        return getFigureClass(className).getDeclaredAnnotation(FigureInfo.class);
    }

    /**
     * Метод для получения аргументов для конструктора фигуры.
     * Метод запрашивает ввод у пользователя только для тех полей,
     * которые помечены аннотацией @FigureFieldInfo. Все вводимые значения
     * проверяются согласно паттерну (переменная pattern)
     * Так же есть возможность в любой момент отменить ввод, нажав - EXIT
     *
     * @param figure Класс фигуры, реализующей интерфейс Figure
     * @return массив введеных пользователем значений типа int[]
     */
    int[] construct(Class<? extends Figure> figure) {
        String s;
        int[] param;
        int c = 0;
        String pattern = "\\d*";
        System.out.printf("Необходимо задать параметры для фигуры: \"%s\" \n",
                figure.getDeclaredAnnotation(FigureInfo.class).figureName());
        System.out.println(TEXT_EXIT);
        Field[] fields = figure.getDeclaredFields();
        param = new int[fields.length];
        for (Field cl : fields) {
            // классы могут содержать не только поля, отмеченные аннотацией
            if (cl.isAnnotationPresent(FigureFieldInfo.class)) {
                while (true) {
                    System.out.printf("Введите значение для параметра: \"%s\" : \n",
                            cl.getDeclaredAnnotation(FigureFieldInfo.class).fieldName());
                    s = scanner.next();
                    // сверяем введеное значение согласно pattern
                    if (s.matches(pattern)) {
                        param[c] = Integer.parseInt(s);
                        break;
                    }
                    // выход
                    if (s.equalsIgnoreCase(EXIT)) {
                        return null;
                    }
                }
            }
            c++;
        }
        return param;
    }

    /**
     * Метод для вычисления методов, отмеченных аннотацией @FigureMainMethod
     * Так же есть возможность в любой момент отменить ввод, нажав - EXIT
     * По ключу вызывается метод из HashMap figureMethods
     *
     * @param figure Ссылка на обьект фигуры типа Figure
     */
    void calculate(Figure figure) throws InvocationTargetException, IllegalAccessException {
        String s;
        HashMap<String, Method> figureMethods;
        Method[] methods = Arrays.stream(figure.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(FigureMainMethod.class)).toArray(Method[]::new);
        do {
            figureMethods = help2(methods);
            s = scanner.next().toUpperCase();
            if (figureMethods.containsKey(s)){
                System.out.printf("Результат: %s \n", figureMethods.get(s).invoke(figure));
            }

        }while (!s.equalsIgnoreCase(EXIT));
    }

    /**
     * Меню для выбора типа вычисления.
     * Строка формируется согласно колличеству методов, полученных из метода calculate.
     * Имя операции береться из поля аннотации FigureMainMethod.methodName()
     * , а горячая клавиша - FigureMainMethod.methodShortName()
     * Далее заполняется HashMap figureMethods, где ключ - это FigureMainMethod.methodShortName(),
     * а значение - Method данного класса.
     */
    private HashMap<String, Method> help2(Method[] methods) {
        HashMap<String, Method> figureMethods = new HashMap<>();
        for (Method method : methods){
                FigureMainMethod fm = method.getDeclaredAnnotation(FigureMainMethod.class);
                figureMethods.put(fm.methodShortName(), method);
                System.out.printf("Для вычисления \"%s\" нажмите \"%s\" \n",
                        fm.methodName(), fm.methodShortName());
        }
        System.out.println(TEXT_EXIT);
        return figureMethods;
    }
}

