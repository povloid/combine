package pk.home.libs.combine.beanutils;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * Muppet Show utils class
 *
 * <p/>
 * User: Kopychenko pavel
 * Date: 27.11.12
 * Time: 15:17
 */
public final class MappedUtils {


    protected static Logger LOG = Logger.getLogger(MappedUtils.class);

    /**
     * Fill mapped object from object and return mapped object
     *
     * @param mappedObject
     * @param object
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T fillMappedObjectRT(T mappedObject, Object object) throws Exception {
        fillMappedObject(mappedObject, object);
        return mappedObject;
    }


    /**
     * Fill mapped object from object
     *
     * @param mappedObject
     * @param object
     * @throws Exception
     */
    public static void fillMappedObject(Object mappedObject, Object object) throws Exception {
        try {
            Class<? extends Object> cs = mappedObject.getClass();

            for (Field field : cs.getDeclaredFields()) {
                if (field.getAnnotations().length > 0) {
                    MappedField mappedField = field.getAnnotation(MappedField.class);

                    if (mappedField != null) {
                        Object value = getProperty(object, mappedField.sourceProperty());
                        setProperty(mappedObject, field.getName(), value);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("Mapped error: " + ex.getMessage());
            throw new Exception(ex);
        }
    }


    /**
     * Fill object object from mapped object and return object
     *
     * @param mappedObject
     * @param object
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T fillObjectRT(Object mappedObject, T object) throws Exception {
        fillObject(mappedObject, object);
        return object;
    }


    /**
     * Fill object object from mapped object
     *
     * @param mappedObject
     * @param object
     * @throws Exception
     */
    public static void fillObject(Object mappedObject, Object object) throws Exception {

        try {
            Class<? extends Object> cs = mappedObject.getClass();

            for (Field field : cs.getDeclaredFields()) {
                if (field.getAnnotations().length > 0) {
                    MappedField mappedField = field.getAnnotation(MappedField.class);

                    if (mappedField != null) {
                        Object value = getProperty(mappedObject, field.getName());
                        setProperty(object, mappedField.sourceProperty(), value);
                    }
                }
            }

        } catch (Exception ex) {
            LOG.error("Mapped error: " + ex.getMessage());
            throw new Exception(ex);
        }
    }


    /**
     * the get property method
     *
     * @param bean
     * @param name
     * @return
     * @throws Exception
     */
    private static Object getProperty(Object bean, String name) throws Exception {
        try {
            return PropertyUtils.getProperty(bean, name);
        } catch (org.apache.commons.beanutils.NestedNullException ex) {
            return null;
        }
    }


    /**
     * the set property method
     * @param bean
     * @param name
     * @param value
     * @throws Exception
     */
    private static void setProperty(Object bean, String name , Object value) throws Exception {
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (org.apache.commons.beanutils.NestedNullException ex) {
            LOG.error("INFO: Null property value for '" + name + "'. Property was not set.");
        }
    }



    /**
     * get mapped id value
     * @param mappedObject
     * @return
     * @throws Exception
     */
    public static Object getId(Object mappedObject) throws  Exception {
        try {
            Class<? extends Object> cs = mappedObject.getClass();

            for (Field field : cs.getDeclaredFields()) {
                if (field.getAnnotations().length > 0) {
                    MappedId mappedField = field.getAnnotation(MappedId.class);

                    if (mappedField != null) {
                       return PropertyUtils.getProperty(mappedObject, field.getName());
                    }
                }
            }

            return null;
        } catch (Exception ex) {
            LOG.error("Mapped error: " + ex.getMessage());
            throw new Exception(ex);
        }
    }


}
