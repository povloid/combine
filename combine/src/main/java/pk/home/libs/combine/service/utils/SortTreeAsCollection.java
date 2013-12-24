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
		
		for(T e: inCollection)
			if(isRootElement(e))
				sortRecursive(inCollection, outCollection, e);
		
		if(desc)
			Collections.reverse(outCollection);
	
		return outCollection;
	}
	
	
	/**
	 * Sort recursive.
	 *
	 * @param inCollection the in collection
	 * @param outCollection the out collection
	 * @param parent the parent
	 * @throws Exception the exception
	 */
	private void sortRecursive(Collection<T> inCollection, List<T> outCollection, T parent) throws Exception{
		
		outCollection.add(parent);
		
		for(T e: inCollection)
			if(isParentElement(e, parent))
				sortRecursive(inCollection, outCollection, e);
	}
	
	
	/**
	 * Checks if is root element.
	 *
	 * @param e the e
	 * @return true, if is root element
	 * @throws Exception the exception
	 */
	protected abstract boolean isRootElement(T e) throws Exception;
	
	/**
	 * Gets the parent.
	 *
	 * @param e the e
	 * @param parent the parent
	 * @return the parent
	 * @throws Exception the exception
	 */
	protected abstract boolean isParentElement(T e, T parent)  throws Exception;

}
