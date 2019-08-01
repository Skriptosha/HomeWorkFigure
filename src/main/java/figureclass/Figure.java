package figureclass;

public interface Figure {
    //must realise class constructor with initialized all class fields!
    int square();
    int perimeter();
    void setParams(int ... args);
}
