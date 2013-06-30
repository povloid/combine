package pk.home.libs.combine.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.dao.PredicatePair;

/**
 * The Class ABaseService.
 *
 * @param <T> the generic type
 * @author Kopychenko Pavel
 * @date Jun 12, 2012
 */
public abstract class ABaseService<T extends Object> {

	/**
	 * Gets the abstract basic dao.
	 *
	 * @return the abstract basic dao
	 */
	public abstract ABaseDAO<T> getAbstractBasicDAO();

	/**
	 * Persist.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T persist(T o) throws Exception {
		getAbstractBasicDAO().persist(o);
		return o;
	}

	/**
	 * Refresh.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T refresh(T o) throws Exception {
		getAbstractBasicDAO().refresh(o);
		return o;
	}

	/**
	 * Merge.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T merge(T o) throws Exception {
		return getAbstractBasicDAO().merge(o);
	}

	
	
	/**
	 * Removes the.
	 *
	 * @param object the object
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(T object) throws Exception {
		getAbstractBasicDAO().remove(
				getAbstractBasicDAO().getManagedEntity(object));
	}

	/**
	 * Find.
	 *
	 * @param key the key
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T find(Object key) throws Exception {
		return getAbstractBasicDAO().find(key);
	}
	
	/**
	 * Find advanced.
	 *
	 * @param cq the cq
	 * @return the <T>
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findAdvanced(CriteriaQuery<T> cq) throws Exception {
		return getAbstractBasicDAO().findAdvanced(cq);
	}
	

	/**
	 * Gets the all entities.
	 *
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities() throws Exception {
		return getAbstractBasicDAO().getAllEntities();
	}

	/**
	 * Gets the all entities.
	 *
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {
		return getAbstractBasicDAO()
				.getAllEntities(orderByAttribute, sortOrder);
	}

	
	
	/**
	 * Gets the all entities.
	 *
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(int firstResult, int maxResults)
			throws Exception {
		return getAbstractBasicDAO().getAllEntities(firstResult, maxResults);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getAbstractBasicDAO().getAllEntities(false, firstResult,
				maxResults, orderByAttribute, sortOrder);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param all the all
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getAbstractBasicDAO().getAllEntities(all, firstResult,
				maxResults, orderByAttribute, sortOrder);
	}

	/**
	 * Count.
	 *
	 * @return the long
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long count() throws Exception {
		return getAbstractBasicDAO().count();
	}
	
	// GET ALL ADVANCED

	/**
	 * Gets the all entities advanced.
	 * 
	 * @param predicatePairs
	 *            the predicate pairs
	 * @param all
	 *            the all
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @param orderByAttribute
	 *            the order by attribute
	 * @param sortOrder
	 *            the sort order
	 * @return the t
	 * @throws Exception
	 *             the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntitiesAdvanced(
			Collection<PredicatePair<T>> predicatePairs, boolean all,
			int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {

		return getAbstractBasicDAO().getAllEntitiesAdvanced(predicatePairs,
				all, firstResult, maxResults, orderByAttribute, sortOrder);
	}

	/**
	 * Count advanced.
	 * 
	 * @param predicatePairs
	 *            the predicate pairs
	 * @return the t
	 * @throws Exception
	 *             the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long countAdvanced(Collection<PredicatePair<T>> predicatePairs)
			throws Exception {

		return getAbstractBasicDAO().countAdvanced(predicatePairs);
	}
	
	
	
}
