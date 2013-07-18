package pk.home.libs.combine.beanutils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kopychenko Pavel - 08.11.2012 13:09:16
 */
public class SimpleRowsObjectsList extends
        ArrayList<SimpleRowObject> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3696824196510896889L;

    private String[][] expressions = new String[][]{};

    public SimpleRowsObjectsList() {
        super();
    }

    /**
     * Create the list
     *
     * @param list
     * @param instructions
     * @throws Exception
     */
    public SimpleRowsObjectsList(List<?> list, String instructions)
            throws Exception {
        super();
        this.expressions = SimpleRowObject.parseExpressions(instructions);
        makeList(list, this.expressions);
    }

    /**
     * Create the list
     *
     * @param list
     * @param expressions
     * @throws Exception
     */
    public SimpleRowsObjectsList(List<?> list, String... expressions)
            throws Exception {
        super();
        this.expressions = SimpleRowObject.parseExpressions(expressions);
        makeList(list, this.expressions);
    }


    /**
     * Create the prepeared clean list
     *
     * @param expressions
     */
    public SimpleRowsObjectsList(String... expressions) {
        super();
        this.expressions = SimpleRowObject.parseExpressions(expressions);
    }

    /**
     * Create the prepeared clean list
     *
     * @param instructions
     */
    public SimpleRowsObjectsList(String instructions) {
        super();
        this.expressions = SimpleRowObject.parseExpressions(instructions);
    }


    /**
     * Parse the list and fill this collection
     *
     * @param list
     * @throws Exception
     */
    public void makeList(List<?> list)
            throws Exception {
        makeList(list, this.expressions);
    }


    /**
     * Parse the list and fill this collection
     *
     * @param list
     * @param instructions
     * @throws Exception
     */
    public void makeList(List<?> list, String instructions)
            throws Exception {
        this.expressions = SimpleRowObject.parseExpressions(instructions);

        makeList(list, this.expressions);
    }

    /**
     * Parse the list and fill this collection
     *
     * @param list
     * @param expressions
     * @throws Exception
     */
    public void makeList(List<?> list, String[][] expressions)
            throws Exception {
        clear();

        for (Object o : list) {
            SimpleRowObject sro = new SimpleRowObject();

            sro.applyExpression(o, expressions);
            add(sro);
        }
    }

    public void setInstructions(String instructions) {
        this.expressions = SimpleRowObject.parseExpressions(instructions);
    }


    public String[][] getExpressions() {
        return expressions;
    }

    public void setExpressions(String[][] expressions) {
        this.expressions = expressions;
    }
}
