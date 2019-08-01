package Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureFieldInfo {

    /**
     * Указываем название поля класса согласно фразе: "Введите значение для параметра: " + fieldName
     *
     * @return Название поля типа String
     */
    String fieldName();
}