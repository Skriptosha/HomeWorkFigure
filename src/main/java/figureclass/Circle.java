package figureclass;


import annotation.FigureFieldInfo;
import annotation.FigureInfo;
import annotation.FigureMainMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static runclass.RunClassSpring.getParam;

/**
 * Круг
 */
@Component
@FigureInfo(figureName = "Круг", figureShortName = "C", NumberOfArgs = 1)
public class Circle implements Figure {

    @FigureFieldInfo(fieldName = "радиус круга")
    private int r;

    @Autowired
    public Circle(){
        setParams(getParam());
    }

    public Circle(int ... args) {
        System.out.println(args.length);
        setParams(args);
    }

    @Override
    @FigureMainMethod(methodName = "Площади", methodShortName = "S")
    public int square() {
        return (int) (Math.PI*Math.pow(r, 2));
    }

    @Override
    @FigureMainMethod(methodName = "Периметра", methodShortName = "P")
    public int perimeter() {
        return (int) (2*Math.PI*r);
    }

    @Override
    public void setParams(int... args) {
        this.r = args[0];
    }
}
