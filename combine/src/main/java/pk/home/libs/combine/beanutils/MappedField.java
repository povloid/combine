package pk.home.libs.combine.beanutils;

/**
 * Mapped marker
 *
 * User: povloid
 * Date: 27.11.12
 * Time: 15:25
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  POJO mapping field
 * @author tt
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedField {

    /**
     * Имя поля в базе
     * @return
     */
    String sourceProperty();

}

