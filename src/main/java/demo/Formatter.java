package demo;

public interface Formatter<T> {

    String format(T t);

    T parse(String string);

}
