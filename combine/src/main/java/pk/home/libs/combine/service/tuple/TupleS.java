package pk.home.libs.combine.service.tuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;

/**
 * 
 * Class TupleS
 *
 * @author Kopychenko Pavel
 *
 * @date Jul 24, 2013
 *
 */
public class TupleS extends ArrayList<TupleElementS> 
		implements Serializable{

	
	/**
	 * Convert.
	 *
	 * @param tuples the tuples
	 * @return the list
	 */
	public static List<TupleS> convert(List<Tuple> tuples) {
		
		List<TupleS> tuplesS = new ArrayList<>();
		
		for(Tuple t: tuples)
			tuplesS.add(new TupleS(t));
		
		return tuplesS;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new tuple s.
	 */
	public TupleS() {
		super();
	}

	/**
	 * Instantiates a new tuple s.
	 *
	 * @param tuple the tuple
	 */
	public TupleS(Tuple tuple) {
		super();
		
		for(TupleElement<?> te : tuple.getElements()){
			add(new TupleElementS(te,tuple.get(te)));
		}
	}

	/**
	 * Instantiates a new tuple s.
	 *
	 * @param initialCapacity the initial capacity
	 */
	public TupleS(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object get(Object key) {
		for(TupleElementS te : this)
			if(te.getAlias().equals(key))
				return te.value;
		
		return null;
	}	
}
