package demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    String value() default "";

    String columnName() default "";

    Class<? extends Formatter> formatter() default Formatter.class;

    String defaultValue() default "";

}