package pk.home.libs.combine.service.tuple;

import java.io.Serializable;

import javax.persistence.TupleElement;

/**
 * 
 * Class TupleElementS
 *
 * @author Kopychenko Pavel
 *
 * @date Jul 24, 2013
 *
 */
public class TupleElementS implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String alias;
	
	Object value;
	
	/**
	 * Instantiates a new tuple element s.
	 */
	public TupleElementS() {
		super();
	}
	
	/**
	 * Instantiates a new tuple element s.
	 *
	 * @param tupleElement the tuple element
	 * @param value the value
	 */
	public TupleElementS(TupleElement<?> tupleElement , Object value) {
		super();
		this.alias = tupleElement.getAlias();
		this.value = value;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
