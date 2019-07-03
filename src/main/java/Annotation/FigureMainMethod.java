package Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureMainMethod {

    /**
     * Имя метода согласно фразе: "Для вычисления " + methodName
     *
     * @return Имя метода типа String
     */
    String methodName();

    /**
     * Короткое имя для обозначения метода
     *
     * @return Короткое имя метода типа String
     */
    String methodShortName();
}
