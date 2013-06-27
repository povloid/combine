package pk.home.libs.combine.dao;

import javax.persistence.metamodel.SingularAttribute;

/**
 * The Class PredicatePair.
 */
public class PredicatePair<X> {
	private SingularAttribute<X, ?> attribute;
	private Object value;

	/**
	 * Instantiates a new predicate pair.
	 *
	 * @param attribute the attribute
	 * @param value the value
	 */
	public PredicatePair(SingularAttribute<X, ?> attribute, Object value) {
		super();
		this.attribute = attribute;
		this.value = value;
	}

	public SingularAttribute<X, ?> getAttribute() {
		return attribute;
	}

	public void setAttribute(SingularAttribute<X, ?> attribute) {
		this.attribute = attribute;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}	