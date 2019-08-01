package config;

import figureclass.Figure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import runclass.Main;

@Component
public class Configurator extends Main {

    @Autowired
    private ApplicationContext context;

    public Figure getBean(Class<?> clazz){
        return context.getBean(clazz.getSimpleName().toLowerCase(), Figure.class);
    }
}
