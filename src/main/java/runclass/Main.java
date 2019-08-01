package runclass;

import annotation.FigureFieldInfo;
import annotation.FigureInfo;
import annotation.FigureMainMethod;
import figureclass.Figure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = {"figureclass", "config", "runclass"}, lazyInit = true)
public class Main {

    private static HashMap<String, Class<?>> figureClasses = new HashMap<>();
    private static final String PACKAGE = Figure.class.getPackage().toString().split(" ")[1];
    private static final String REGEX = ".java";
    private static final String FIGURE_CLASS_PATH = "src\\main\\java\\" + PACKAGE;
    static final String EXIT = "Q";
    private static final String TEXT_EXIT = "Для возврата в предыдущее меню нажмите \"" + EXIT + "\"";
    private static ApplicationContext context;

    /**
     * Геттер для HashMap figureClasses.
     *
     * @return HashMap figureClasses;
     */
    static HashMap<String, Class<?>> getFigureClasses() {
        return figureClasses;
    }

    @Bean
    public ApplicationContext getContext() {
        return context;
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
            // берем только файлы с расширением "REGEX" и проверяем что нужный класс реализует интерфейс Figure
            Path[] paths = Files.list(Paths.get(FIGURE_CLASS_PATH)).
                    filter(p -> p.getFileName().toString().endsWith(REGEX) &&
                            Figure.class.isAssignableFrom(getFigureClass(p.getFileName().toString().split(REGEX)[0])))
                    .toArray(Path[]::new);
            for (Path path : paths) {
                Class<? extends Figure> clazz = getFigureClass(path.getFileName().toString().split(REGEX)[0])
                        .asSubclass(Figure.class);
                // проверяем наличие необходимых аннотаций
                if (clazz.isAnnotationPresent(FigureInfo.class)
                        && isHaveAnnotation(clazz.getDeclaredMethods(), FigureMainMethod.class)
                        && isHaveAnnotation(clazz.getDeclaredFields(), FigureFieldInfo.class)) {
                    figureClasses.put(clazz.getDeclaredAnnotation(FigureInfo.class).figureShortName(), clazz);
                }
            }
            scanner = new Scanner(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запуск программы, метод main
     *
     * @param args аргументы коммандной строки (не учитываются)
     */
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, ClassNotFoundException  {
        context = new AnnotationConfigApplicationContext(Main.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        context.getBean("runClassSpring", RunClassSpring.class).run();
        //        new RunClassAnnotation().run();
    }

    /**
     * Перегруженный метод для проверки наличия аннотации у метода
     *
     * @param methods         массив методов для проверки
     * @param annotationClass аннотация, которую надо проверить
     * @return тру, если найдена хотя бы 1 аннотация annotationClass
     */
    private static boolean isHaveAnnotation(Method[] methods, Class<? extends Annotation> annotationClass) {
        for (Method m : methods) {
            if (m.isAnnotationPresent(annotationClass)) return true;
        }
        return false;
    }

    /**
     * Перегруженный метод для проверки наличия аннотации у поля класса
     *
     * @param fields          массив полей класса для проверки
     * @param annotationClass аннотация, которую надо проверить
     * @return тру, если найдена хотя бы 1 аннотация annotationClass
     */
    private static boolean isHaveAnnotation(Field[] fields, Class<? extends Annotation> annotationClass) {
        for (Field f : fields) {
            if (f.isAnnotationPresent(annotationClass)) return true;
        }
        return false;
    }

    static Scanner scanner;


    /**
     * Меню выбора фигуры
     * Строка формируется согласно колличеству классов фигур, полученных в статическом блоке класса Main -
     * HashMap figureClasses.
     * Далее просто выводится поле figureName() аннотации FigureInfo и поле figureShortName()
     * (оно же является ключом в figureClasses)
     */
    void help() {
        System.out.println("Выбирите фигуру: ");
        for (Map.Entry<String, Class<?>> entry : figureClasses.entrySet()) {
            System.out.printf("Для выбора фигуры \"%s\" нажмите \"%s\" \n",
                    getFigureAnnotation(entry.getValue().getSimpleName()).figureName(), entry.getKey());
        }
        System.out.println("Для выхода нажмите " + EXIT);
    }

    /**
     * Поиск класса типа Class<?> по названию класса
     * Применяется для поиска классов фигур
     *
     * @param className название класса, должен находиться в пакете figureclass
     * @return класс Class<?> из пакета figureclass
     */
    static Class<?> getFigureClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(PACKAGE + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert clazz != null;
        return clazz;
    }

    /**
     * Получение аннотации FigureInfo класса из пакета figureclass.
     * Использует метод getFigureClass.
     *
     * @param className название класса, должен находиться в пакете figureclass
     * @return аннотация FigureInfo класса className из пакета figureclass
     */
    static FigureInfo getFigureAnnotation(String className) {
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
    public int[] construct(Class<?> figure) {
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
                c++;
            }

        }
        return param;
    }

    /**
     * Метод для вычисления методов, отмеченных аннотацией @FigureMainMethod
     * Так же есть возможность в любой момент отменить ввод, нажав - EXIT
     * Далее заполняется HashMap figureMethods, где ключ - это FigureMainMethod.methodShortName(),
     * а значение - Method данного класса. По ключу вызывается метод из HashMap figureMethods
     *
     * @param figure Ссылка на обьект фигуры типа Figure
     */
    void calculate(Object figure) throws InvocationTargetException, IllegalAccessException {
        String s;
        HashMap<String, Method> figureMethods = new HashMap<>();
        List<Method> methods = Arrays.stream(figure.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(FigureMainMethod.class)).collect(Collectors.toList());
        for (Method method : methods) {
            FigureMainMethod fm = method.getDeclaredAnnotation(FigureMainMethod.class);
            figureMethods.put(fm.methodShortName(), method);
        }
        do {
            help2(figureMethods);
            s = scanner.next().toUpperCase();
            if (figureMethods.containsKey(s)) {
                System.out.printf("Результат: %s \n", figureMethods.get(s).invoke(figure));
            }

        } while (!s.equalsIgnoreCase(EXIT));
    }

    /**
     * Меню для отображения типа вычисления.
     * Строка формируется согласно колличеству методов, полученных из метода calculate.
     * Имя операции береться из поля аннотации FigureMainMethod.methodName(),
     * а горячая клавиша - FigureMainMethod.methodShortName()
     */
    private void help2(HashMap<String, Method> figureMethods) {
        for (Map.Entry<String, Method> method : figureMethods.entrySet()) {
            System.out.printf("Для вычисления \"%s\" нажмите \"%s\" \n",
                    method.getValue().getDeclaredAnnotation(FigureMainMethod.class).methodName(), method.getKey());
        }
        System.out.println(TEXT_EXIT);
    }
}

