package pk.home.libs.combine.service;

import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.transaction.annotation.Transactional;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;


public abstract class ABaseService<T extends Object> {

	public abstract ABaseDAO<T> getAbstractBasicDAO();

	@Transactional
	public T persist(T o) throws Exception {
		getAbstractBasicDAO().persist(o);
		return o;
	}

	@Transactional
	public T refresh(T o) throws Exception {
		getAbstractBasicDAO().refresh(o);
		return o;
	}

	@Transactional
	public T merge(T o) throws Exception {
		return getAbstractBasicDAO().merge(o);
	}

	@Transactional
	public void remove(T object) throws Exception {
		getAbstractBasicDAO().remove(getAbstractBasicDAO().getManagedEntity(object));
	}

	@Transactional
	public T find(Object key) throws Exception {
		return getAbstractBasicDAO().find(key);
	}

	@Transactional
	public List<T> getAllEntities() throws Exception {
		return getAbstractBasicDAO().getAllEntities();
	}

	@Transactional
	public List<T> getAllEntities(SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder) throws Exception {
		return getAbstractBasicDAO().getAllEntities(orderByAttribute, sortOrder);
	}
	
	
	@Transactional
	public List<T> getAllEntities( int firstResult, int maxResults)
			throws Exception {
		return getAbstractBasicDAO().getAllEntities( firstResult, maxResults);
	}
	
	@Transactional
	public List<T> getAllEntities(int firstResult, int maxResults,   
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getAbstractBasicDAO().getAllEntities(false, firstResult, maxResults, orderByAttribute, sortOrder);
	}
	

	@Transactional
	public long count() throws Exception {
		return getAbstractBasicDAO().count();
	}
}
