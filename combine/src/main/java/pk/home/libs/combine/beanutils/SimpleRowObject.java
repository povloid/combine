package pk.home.libs.combine.beanutils;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The simple row object
 * This object provide the simple mapping to row map collection
 *
 * @author Kopychenko Pavel - 08.11.2012 13:09:26
 */
public class SimpleRowObject extends HashMap<String, Object> implements
        Serializable {

    private static final long serialVersionUID = -4616996498912523454L;


    public static final String NULL_VALUE_REPLACEMENT = "...";

    public static final String EXPRESSIONS_DELIMITER = ";";
    public static final String MAP_TO = "=>";
    public static final String MAP_TO_STRING = "#String=>";
    public static final char MAP_TO_AS_STRING_LITERAL_PREFISX = '`';

    public static final String MAP_TO_ELEMENTS_DELIMITER = ",";
    public static final String MAP_TO_OBJECTS_ARRAY = "#ObjArray=>";
    public static final String MAP_TO_MAP = "#Map=>";
    public static final String MAP_TO_MAP_KEY_DELIMITER = "->";
    public static final int MAP_TO_MAP_KEY_OPERAND_POSITION = 1;
    public static final int MAP_TO_MAP_PROPERTY_OPERAND_POSITION = 0;


    public static final String MAP_TO_STRING_INSERT_MARKER_START = "${";
    public static final String MAP_TO_STRING_INSERT_MARKER_END = "}";
    public static final String PROPERTY_ID = "id";
    public static final String COPY_AS_NAME_SOURCE = ">";

    public static final int OP_ARRAY_SIZE = 3;
    public static final int OP_ID_POSITION = 2;
    public static final int OP_RIGHT_OPERAND_POSITION = 1;
    public static final int OP_LEFT_OPERAND_POSITION = 0;

    public static final String STRING_FORMAT_TYPES_DELIMITER = ":";
    public static final int STRING_FORMAT_TYPE_ARRAY_POSITION = 1;

    public static final int STRING_FORMAT_PROPERTY_NAMES_ARRAY_SIZE = 2;
    public static final int STRING_FORMAT_PROPERTY_NAME_POSITION = 0;
    public static final int STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION = 1;

    public static final String PROPERTY_AS_INVOKE_METHOD = "(M)";

    protected Logger LOG = Logger.getLogger(this.getClass());


    public static enum StringFormatTypes {
        STRING, TIME, DATE, TIMESTAMP
    }

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final static SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Constract
     *
     * @param entity
     * @param id
     * @param instructions
     * @throws Exception
     */
    public SimpleRowObject(Object entity, Object id, String instructions)
            throws Exception {
        super();

        put(PROPERTY_ID, id);

        applyExpression(entity, parseExpressions(instructions));
    }

    /**
     * Constract
     *
     * @param entity
     * @param instructions
     * @throws Exception
     */
    public SimpleRowObject(Object entity, String instructions)
            throws Exception {
        super();

        applyExpression(entity, parseExpressions(instructions));
    }

    /**
     * Constract
     *
     * @param entity
     * @param id
     * @param expressions
     * @throws Exception
     */
    public SimpleRowObject(Object entity, Object id, String... expressions)
            throws Exception {
        super();

        put(PROPERTY_ID, id);

        applyExpression(entity, parseExpressions(expressions));
    }

    /**
     * Constract
     *
     * @param entity
     * @param expressions
     * @throws Exception
     */
    public SimpleRowObject(Object entity, String... expressions)
            throws Exception {
        super();

        applyExpression(entity, parseExpressions(expressions));
    }


    /**
     * get expressions
     *
     * @param instructions
     * @return
     */
    public static String[][] parseExpressions(String instructions) {

        String[] expressions = instructions.split(EXPRESSIONS_DELIMITER);

        String[][] result = new String[expressions.length][];

        int index = 0;
        for (String expression : expressions) {
            result[index] = parseExpression(expression);
            ++index;
        }

        return result;
    }


    /**
     * get expressions
     *
     * @param instructions
     * @return
     */
    public static String[][] parseExpressions(String... instructions) {

        String[][] result = new String[instructions.length][];

        int index = 0;
        for (String expression : instructions) {
            result[index] = parseExpression(expression);
            ++index;
        }

        return result;
    }


    /**
     * Parse params and operation type
     *
     * @param expression
     * @return
     */
    private static String[] parseExpression(String expression) {

        String[] res = null;

        // Сортировка по укорочению вхождений. Обязательно!
        if (expression.contains(MAP_TO_STRING)) {
            res = Arrays.copyOf(expression.split(MAP_TO_STRING), OP_ARRAY_SIZE);
            res[OP_ID_POSITION] = MAP_TO_STRING;
        } else if (expression.contains(MAP_TO_OBJECTS_ARRAY)) {
            res = Arrays.copyOf(expression.split(MAP_TO_OBJECTS_ARRAY), OP_ARRAY_SIZE);
            res[OP_ID_POSITION] = MAP_TO_OBJECTS_ARRAY;
        } else if (expression.contains(MAP_TO_MAP)) {
            res = Arrays.copyOf(expression.split(MAP_TO_MAP), OP_ARRAY_SIZE);
            res[OP_ID_POSITION] = MAP_TO_MAP;
        } else if (expression.contains(MAP_TO)) {
            res = Arrays.copyOf(expression.split(MAP_TO), OP_ARRAY_SIZE);
            res[OP_ID_POSITION] = MAP_TO;
        }

        return res;
    }

    /**
     * Applay expressions to this instance
     *
     * @param entity
     * @param res
     * @throws Exception
     */
    public void applyExpression(Object entity, String[][] res)
            throws Exception {
        for (String[] expr : res) {
            // Сортировка по укорочению вхождений. Обязательно!
            if (expr[OP_ID_POSITION].equals(MAP_TO_STRING)) {
                mapToString(entity, expr[OP_LEFT_OPERAND_POSITION].trim(), expr[OP_RIGHT_OPERAND_POSITION].trim());
            } else if (expr[OP_ID_POSITION].equals(MAP_TO_OBJECTS_ARRAY)) {
                mapToArray(entity, expr[OP_LEFT_OPERAND_POSITION].trim(), expr[OP_RIGHT_OPERAND_POSITION].trim());
            } else if (expr[OP_ID_POSITION].equals(MAP_TO_MAP)) {
                mapToMap(entity, expr[OP_LEFT_OPERAND_POSITION].trim(), expr[OP_RIGHT_OPERAND_POSITION].trim());
            } else if (expr[OP_ID_POSITION].equals(MAP_TO)) {
                mapTo(entity, expr[OP_LEFT_OPERAND_POSITION].trim(), expr[OP_RIGHT_OPERAND_POSITION].trim());
            }
        }
    }

    /**
     * Map to simple objects array
     *
     * @param entity
     * @param string
     * @param key
     * @throws Exception
     */
    private void mapToMap(Object entity, String string, String key) throws Exception {
        LOG.debug(">>> mapToMap");

        String[] expressions = string.trim().split(MAP_TO_ELEMENTS_DELIMITER);

        Map<String, Object> map = new HashMap<String, Object>();

        for (String expr : expressions) {

            String[] operands = null;

            try {
                operands = expr.trim().split(MAP_TO_MAP_KEY_DELIMITER);
                Object properValue = getProperty(entity, operands[MAP_TO_MAP_PROPERTY_OPERAND_POSITION].trim());
                map.put(operands[MAP_TO_MAP_KEY_OPERAND_POSITION].trim(), properValue);
            } catch (org.apache.commons.beanutils.NestedNullException ex) {
                map.put(operands[MAP_TO_MAP_KEY_OPERAND_POSITION].trim(), null);
            } catch (Exception ex) {
                LOG.error("Ошибка при обработке значения:" + operands[MAP_TO_MAP_KEY_OPERAND_POSITION] + "\n" + ex.getMessage());
                throw new Exception(ex);
            }
        }

        put(key, map);
    }

    /**
     * Map to simple objects array
     *
     * @param entity
     * @param string
     * @param key
     * @throws Exception
     */
    private void mapToArray(Object entity, String string, String key) throws Exception {
        LOG.debug("mapToArray");

        String[] properties = string.trim().split(MAP_TO_ELEMENTS_DELIMITER);

        List<Object> list = new ArrayList<Object>();

        for (String propertyName : properties) {
            try {
                LOG.debug(propertyName);
                list.add(getProperty(entity, propertyName));
            } catch (org.apache.commons.beanutils.NestedNullException ex) {
                list.add(null);
            } catch (Exception ex) {
                LOG.error("Ошибка при обработке значения:" + propertyName + "\n" + ex.getMessage());
                throw new Exception(ex);
            }

        }

        put(key, list.toArray());
    }


    /**
     * Map several properties as string
     *
     * @param entity
     * @param string
     * @param key
     * @throws Exception
     */
    private void mapToString(Object entity, String string, String key) throws Exception {
        LOG.debug("mapToString");

        StringBuffer sbResult = new StringBuffer(string);
        List<String[]> propertyNames = new ArrayList<String[]>();

        int startIndex = 0, endIndex = 0;

        do {
            startIndex = sbResult.indexOf(MAP_TO_STRING_INSERT_MARKER_START, endIndex);
            endIndex = sbResult.indexOf(MAP_TO_STRING_INSERT_MARKER_END, startIndex);

            if (startIndex != -1 && endIndex == -1)
                throw new Exception("The incorrect expression: " + sbResult);

            if (startIndex == -1) // if not found then break cycle
                break;

            {
                String subString = sbResult.substring(startIndex + 2, endIndex);

                String[] propertyName = new String[STRING_FORMAT_PROPERTY_NAMES_ARRAY_SIZE];
                propertyName[STRING_FORMAT_PROPERTY_NAME_POSITION] = subString;
                propertyName[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION] = subString.trim();
                propertyNames.add(propertyName);

                LOG.debug(subString);
            }


        } while (true);

        for (String[] pn : propertyNames) {

            String exprRestored = MAP_TO_STRING_INSERT_MARKER_START + pn[STRING_FORMAT_PROPERTY_NAME_POSITION] + MAP_TO_STRING_INSERT_MARKER_END;
            int subStringIStartIndex = sbResult.indexOf(exprRestored);

            StringFormatTypes formatType = StringFormatTypes.STRING;

            try {

                // Parse format type


                if (pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION].contains(STRING_FORMAT_TYPES_DELIMITER)) {
                    String[] operands = pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION].split(STRING_FORMAT_TYPES_DELIMITER);

                    if (operands[STRING_FORMAT_TYPE_ARRAY_POSITION] != null) {
                        formatType = StringFormatTypes.valueOf(operands[STRING_FORMAT_TYPE_ARRAY_POSITION].trim().toUpperCase());
                    }

                    // clear format info
                    pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION] = pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION]
                            .replaceAll(operands[STRING_FORMAT_TYPE_ARRAY_POSITION], "")
                            .replaceAll(STRING_FORMAT_TYPES_DELIMITER, "");
                }

                String value = convertToString(getProperty(entity, pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION]), formatType);
                sbResult.replace(subStringIStartIndex, subStringIStartIndex + exprRestored.length(),
                        value);
            } catch (org.apache.commons.beanutils.NestedNullException ex) {
                sbResult.replace(subStringIStartIndex, subStringIStartIndex + exprRestored.length(), NULL_VALUE_REPLACEMENT);
            } catch (Exception ex) {
                LOG.error("Ошибка при обработке значения в комбинированной строке на свойстве:"
                        + exprRestored + " (" + pn[STRING_FORMAT_PROPERTY_NAME_CLEAN_POSITION] + ") \n" + ex.getMessage());
                throw new Exception(ex);
            }
        }

        put(key, sbResult.toString());
    }

    /**
     * make map to - function
     *
     * @param entity
     * @param propertyName
     * @param key
     * @throws Exception
     */
    public void mapTo(Object entity, String propertyName, String key)
            throws Exception {
        LOG.debug("mapTo");
        LOG.debug(propertyName);

        try {
            if (propertyName.equals(PROPERTY_ID))
                put(PROPERTY_ID,
                        getProperty(entity, propertyName));
            else if (key.equals(COPY_AS_NAME_SOURCE))
                put(cleanSourceOperand(propertyName),
                        getProperty(entity, propertyName));
            else
                put(key, getProperty(entity, propertyName));
        } catch (org.apache.commons.beanutils.NestedNullException ex) {

            if (propertyName.equals(PROPERTY_ID))
                put(PROPERTY_ID, null);
            else if (key.equals(COPY_AS_NAME_SOURCE))
                put(propertyName, null);
            else
                put(key, null);

        } catch (Exception ex) {
            LOG.error("Ошибка при обработке значения:" + propertyName + "\n" + ex.getMessage());
            throw new Exception(ex);
        }
    }

    public SimpleRowObject() {
        super();
    }

    /**
     * Convert type to string
     *
     * @param value
     * @param formatType
     * @return
     * @throws Exception
     */
    private String convertToString(Object value, StringFormatTypes formatType) throws Exception {
        try {

            if (value == null)
                return NULL_VALUE_REPLACEMENT;

            switch (formatType) {
                case STRING:
                    return value + "";
                case DATE:
                    return dateFormat.format(value);
                case TIME:
                    return timeFormat.format(value);
                case TIMESTAMP:
                    return timeStampFormat.format(value);

                default:
                    return value + "";
            }


        } catch (Exception ex) {
            LOG.error("Ошибка конвертировании типа " + formatType + " значения " + value + " в строку." + "\n" + ex.getMessage());
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
    private Object getProperty(Object bean, String name) throws Exception {

        //LOG.debug("" + name.trim().charAt(0));

        if (name.trim().charAt(0) == MAP_TO_AS_STRING_LITERAL_PREFISX) {
           return name.replaceFirst(MAP_TO_AS_STRING_LITERAL_PREFISX + "","");
        } else if (name.contains(PROPERTY_AS_INVOKE_METHOD)) {
            if (name.contains(".")) {
                StringBuilder propertyName = new StringBuilder(name);
                propertyName.delete(propertyName.lastIndexOf("."), propertyName.length());
                bean = PropertyUtils.getProperty(bean, propertyName.toString());
                name = name.replaceAll(propertyName.toString() + ".", "");
            }
            return MethodUtils.invokeMethod(bean, name.replace(PROPERTY_AS_INVOKE_METHOD, ""), null);
        } else {
            return PropertyUtils.getProperty(bean, name);
        }
    }


    /**
     * Clean source string from spec sequences
     * exaple: "method(m)" -->  "method"
     *
     * @param sourceOperand
     * @return
     */
    private String cleanSourceOperand(String sourceOperand) {
        return sourceOperand.replace(PROPERTY_AS_INVOKE_METHOD, "");
    }
}
