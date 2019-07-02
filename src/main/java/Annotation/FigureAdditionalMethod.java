package Annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureAdditionalMethod {

    /**
     * Название метода (должно соответствовать самому методу!)
     *
     * @return Название метода типа String
     */
    String methodName();

    /**
     * Массив типов аргументов метода. Должен содержать типы всех методов массива
     *
     * @return массив типов типа Class
     */
    Class<?> argsClass();

    /**
     * Должен ли метод блокировать дальнейшее выполнение программы? Или метод просто отображает какую-либо информацию
     * (выполняет вычисления)
     *
     * @return тру если да, фалс если нет
     */
    boolean isBlock();

}
