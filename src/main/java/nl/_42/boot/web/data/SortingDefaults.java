package nl._42.boot.web.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Define multiple sorting defaults for an entity.
 *
 * @author Jeroen van Schagen
 * @since Sep 3, 2015
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface SortingDefaults {

    /**
     * The sorting defaults.
     *
     * @return the defaults
     */
    SortingDefault[] value() default {};

}
