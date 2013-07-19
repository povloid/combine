package pk.home.libs.combine.dao;


import javax.persistence.metamodel.SingularAttribute;

/**
 * 
 * Class SelectionAtribute
 *
 * @author Kopychenko Pavel
 *
 * @date Jul 19, 2013
 * 
 * @param <T>
 */
public class SelectionAtribute<T> {

	private SingularAttribute<T, ?> root;
	
	/** The selections attribute. */
	private SingularAttribute<?, ?>[] path;
	
	public SelectionAtribute(SingularAttribute<T, ?> root) {
		super();
		this.root = root;
	}

	/**
	 * Instantiates a new selection attribute.
	 *
	 * @param selection the selection
	 */
	public SelectionAtribute(SingularAttribute<T, ?> root, SingularAttribute<?, ?>... path) {
		super();
		this.root = root;
		this.path = path;
	}


	public SingularAttribute<T, ?> getRoot() {
		return root;
	}


	public void setRoot(SingularAttribute<T, ?> root) {
		this.root = root;
	}


	public SingularAttribute<?, ?>[] getPath() {
		return path;
	}


	public void setPath(SingularAttribute<?, ?>[] path) {
		this.path = path;
	}

	
}
