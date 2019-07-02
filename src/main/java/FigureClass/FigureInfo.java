package FigureClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FigureInfo {

    /**
     * Имя фигуры
     *
     * @return Имя фигуры типа Стринг
     */
    String nameFigure();

    /**
     * Короткое имя для обозначения фигуры
     *
     * @return Короткое имя фигуры типа Стринг
     */
    String shortNameFigure();

    /**
     * Число аргументов для контруктора
     *
     * @return Число аргументов типа int
     */
    int NumberOfArgs();

}
