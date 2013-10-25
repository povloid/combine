package pk.home.libs.combine.web.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DualListModel;


/**
 * 
 * Class ManyToManyModel
 * 
 * @author Kopychenko Pavel
 * 
 * @date 25 окт. 2013 г.
 * 
 */
public abstract class ManyToManyModel<T, ID> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Map<String, ID> map = new HashMap<String, ID>();
	private DualListModel<String> dList;

	/**
	 * Populate resort types.
	 */
	public void populateDList() throws Exception{
		map.clear();

		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();

			for (T o : getSourceCollection()) {
				source.add(getAsString(o));
				map.put(getAsString(o), getId(o));
			}

			for (T o : getTargetCollection()) {
				target.add(getAsString(o));
				map.put(getAsString(o), getId(o));
			}

			source.removeAll(target);

		
		this.dList = new DualListModel<String>(source, target);
	}

	public abstract Collection<T> getSourceCollection() throws Exception;

	public abstract Collection<T> getTargetCollection() throws Exception;

	public abstract String getAsString(T o) throws Exception;

	public abstract ID getId(T o) throws Exception;
	
	
	/**
	 * Populate edited.
	 *
	 * @throws Exception the exception
	 */
	public void populateEdited() throws Exception {
		getTargetCollection().clear();

		for (String s : dList.getTarget()) {
			ID id = map.get(s);
			
			T rt = find(id);
			
			getTargetCollection().add(rt);
		}
	}
	
	public abstract T find(ID id) throws Exception;

	

	// get's and set's -------------------------------------------

	public DualListModel<String> getdList() {
		return dList;
	}
	
	public void setdList(DualListModel<String> dList) {
		this.dList = dList;
	}
	
}
