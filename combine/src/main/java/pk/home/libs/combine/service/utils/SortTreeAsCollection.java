package pk.home.libs.combine.service.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class SortTreeAsCollection<T,P> {
	
	

	/**
	 * Sort.
	 *
	 * @param inCollection the in collection
	 * @param desc the desc
	 * @return the collection
	 * @throws Exception the exception
	 */
	public List<T> sort(Collection<T> inCollection, boolean desc) throws Exception {
		
		List<T> outCollection = new ArrayList<>();
		
		for(T a: inCollection)
			if(getParentElement(a) == null)
				sortRecursive(inCollection, outCollection, a);
		
		if(desc)
			Collections.reverse((List<?>) outCollection);
	
		return outCollection;
	}
	
	
	
	/**
	 * Sort recursive.
	 *
	 * @param inCollection the in collection
	 * @param outCollection the out collection
	 * @param parent the parent
	 */
	private void sortRecursive(Collection<T> inCollection, List<T> outCollection, T parent){
		
		outCollection.add(parent);
		
		for(T a: inCollection)
			if(getParentElement(a) != null && getParentElement(a).equals(getElement(parent)))
				sortRecursive(inCollection, outCollection, a);
	}
	
	
	/**
	 * Gets the parent.
	 *
	 * @param o the o
	 * @return the parent
	 */
	protected abstract P getParentElement(T o);
	
	/**
	 * Gets the element.
	 *
	 * @param o the o
	 * @return the element
	 */
	protected abstract P getElement(T o);
	

}
