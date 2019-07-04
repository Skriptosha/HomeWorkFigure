package Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureInfo {

    /**
     * Имя фигуры согласно фразе: "Необходимо задать параметры для фигуры: figureName" и
     * "Выбирите фигуру: figureName"
     *
     * @return Имя фигуры типа String
     */
    String figureName();

    /**
     * Короткое имя для обозначения фигуры
     *
     * @return Короткое имя фигуры типа String
     */
    String figureShortName();

    /**
     * Число аргументов для контруктора
     *
     * @return Число аргументов типа int
     */
    int NumberOfArgs();

}
