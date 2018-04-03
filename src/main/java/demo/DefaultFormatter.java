package demo;

public class DefaultFormatter implements Formatter<Object> {

    @Override
    public String format(Object t) {
        return t.toString();
    }

    @Override
    public Object parse(String string) {
        throw new UnsupportedOperationException("非String类型属性使用指定类型的Format");
    }

}
