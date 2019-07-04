package Annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Метод может возвращать значения двух типов - boolean и void
 * Метод будет считаться блокирующим, если тип возвращаемого значения метода будет boolean. (будет требовать
 * у пользователя ввести новые значения через метод construct)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureAdditionalMethod {

    /**
     * Название метода (должно соответствовать самому методу!)
     *
     * @return Название метода типа String
     */
    String methodName();
}
