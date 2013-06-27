package pk.home.libs.combine.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ABaseDAO.
 *
 * @param <T> the generic type
 * 
 * @author Kopychenko Pavel
 * @date Jun 12, 2012
 */
public abstract class ABaseDAO<T extends Object> {

	/**
	 * For all queries executed by this class, it is assumed that the desired
	 * first row in the returned results is the actual first row returned by the
	 * database. In methods that allow for the first row to be set, if null or a
	 * negative number is passed, then this default value is used.
	 */
	public static final int DEFAULT_FIRST_RESULT_INDEX = 0;
	/**
	 * This is the default value used for initially determining the maximum
	 * number of rows returned in result sets for methods in this class where
	 * the maximum number of results is not passed as a parameter.
	 * 
	 * @see getDefaultMaxResults()
	 */
	public static final int DEFAULT_MAX_RESULTS = 200;

	/** The default max results. */
	private int defaultMaxResults = DEFAULT_MAX_RESULTS;

	/**
	 * The {@link EntityManager} which is used by all query manipulation and
	 * execution in this DAO.
	 * 
	 * @return the {@link EntityManager}
	 */
	public abstract EntityManager getEntityManager();

	/**
	 * Получить тип.
	 *
	 * @return the t class
	 */
	protected abstract Class<T> getTClass();

	/**
	 * Получить первичный ключ.
	 *
	 * @param o the o
	 * @return the primary key
	 */
	public abstract Object getPrimaryKey(T o);

	/**
	 * The Enum SortOrderType.
	 */
	public enum SortOrderType {
		ASC, DESC
	}

	/**
	 * Gets the all entities.
	 *
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities() throws Exception {
		return getAllEntities(true, -1, -1, null, SortOrderType.ASC);
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
		return getAllEntities(true, -1, -1, orderByAttribute, sortOrder);
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
		return getAllEntities(false, firstResult, maxResults, null,
				SortOrderType.ASC);
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
		return getAllEntities(false, firstResult, maxResults, orderByAttribute,
				sortOrder);
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

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только	так !
																		
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());

		if (orderByAttribute != null) {
			switch (sortOrder) {
			case DESC:
				cq.orderBy(cb.desc(t.get(orderByAttribute)));
				break;
			case ASC:
				cq.orderBy(cb.asc(t.get(orderByAttribute)));
				break;

			default:
				cq.orderBy(cb.asc(t.get(orderByAttribute)));
				break;
			}

		}

		TypedQuery<T> q = getEntityManager().createQuery(cq);

		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult >= 0 ? firstResult : 0);
		}
		return q.getResultList();
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
		if (key == null) {
			return null;
		} else {
			return getEntityManager().find(getTClass(), key);
		}
	}
	
	/**
	 * Gets the single result.
	 * 
	 * @param cq
	 *            the cq
	 * @return the single result
	 * @throws Exception
	 *             the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object findAdvancedObj(CriteriaQuery<Object> cq) throws Exception {

		// create query
		// ----------------------------------------------------------------------------
		TypedQuery<Object> q = getEntityManager().createQuery(cq);

		return q.getSingleResult();
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
		TypedQuery<T> q = getEntityManager().createQuery(cq);
		return q.getSingleResult();
	}
	
	/**
	 * Find advanced.
	 *
	 * @param attribute the attribute
	 * @return the <T>
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findAdvanced(SingularAttribute<T, ?> attribute, Object value) throws Exception {
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только	так !
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());

		cb.equal(t.get(attribute), value);
		
		TypedQuery<T> q = getEntityManager().createQuery(cq);
		return q.getSingleResult();
	}
	
	/**
	 * Find advanced.
	 * 
	 * @param attribute1
	 *            the attribute1
	 * @param value1
	 *            the value1
	 * @param attribute2
	 *            the attribute2
	 * @param value2
	 *            the value2
	 * @return the <T>
	 * @throws Exception
	 *             the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findAdvanced(SingularAttribute<T, ?> attribute1, Object value1,
			SingularAttribute<T, ?> attribute2, Object value2) throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только
																		// так !
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());

		cq.where(cb.and(
				cb.equal(t.get(attribute1), value1),
				cb.equal(t.get(attribute2), value2)));

		TypedQuery<T> q = getEntityManager().createQuery(cq);
		return q.getSingleResult();
	}
	
	/**
	 * Find advanced.
	 *
	 * @param attribute1 the attribute1
	 * @param value1 the value1
	 * @param attribute2 the attribute2
	 * @param value2 the value2
	 * @param attribute3 the attribute3
	 * @param value3 the value3
	 * @return the <T>
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findAdvanced(SingularAttribute<T, ?> attribute1, Object value1,
			SingularAttribute<T, ?> attribute2, Object value2, SingularAttribute<T, ?> attribute3, Object value3) throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только
																		// так !
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());

		cq.where(cb.and(
				cb.equal(t.get(attribute1), value1),
				cb.equal(t.get(attribute2), value2),
				cb.equal(t.get(attribute3), value3)));

		TypedQuery<T> q = getEntityManager().createQuery(cq);
		return q.getSingleResult();
	}
	
	
	
	
	/**
	 * Find advanced.
	 *
	 * @param predicatePairs the predicate pairs
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findAdvanced(PredicatePair<T>[] predicatePairs) throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // !
																		
		CriteriaQuery<T> cq = cb.createQuery(getTClass());
		Root<T> t = cq.from(getTClass());
		
		List<Predicate> predicates = new ArrayList<>();
		
		for(PredicatePair<T> p : predicatePairs){
			predicates.add(cb.equal(t.get(p.getAttribute()), p.getValue()));
		}
		
		cq.where(cb.and(predicates.toArray(new Predicate[]{})));

		TypedQuery<T> q = getEntityManager().createQuery(cq);
		return q.getSingleResult();
	}
	

	/**
	 * Gets the managed entity.
	 *
	 * @param unmanagedBean the unmanaged bean
	 * @return the managed entity
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T getManagedEntity(T unmanagedBean) throws Exception {
		return getEntityManager().find(getTClass(),
				getPrimaryKey(unmanagedBean));
	}

	/**
	 * Count.
	 *
	 * @return the long
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long count() throws Exception {
		CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<T> rt = cq.from(getTClass());
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).longValue();
	}

	/**
	 * Count.
	 *
	 * @param rt the rt
	 * @param cq the cq
	 * @return the long
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public long count(Root<T> rt, CriteriaQuery<Object> cq) throws Exception {
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).longValue();
	}

	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Add new o.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T persist(T o) throws Exception {
		getEntityManager().persist(o);
		return o;
	}

	/**
	 * Refresh the deatached bean.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T refresh(T o) throws Exception {
		getEntityManager().refresh(o);
		return o;
	}

	/**
	 * The update bean.
	 *
	 * @param o the o
	 * @return the t
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T merge(T o) throws Exception {
		o = getEntityManager().merge(o);
		return o;
	}

	/**
	 * Delete bean.
	 *
	 * @param o the o
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(T o) throws Exception {
		getEntityManager().remove(o);
		getEntityManager().flush();
	}

	// ---------------------------------------------------------------------------------

	/**
	 * Sets the default max results.
	 *
	 * @param defaultMaxResults the new default max results
	 */
	public void setDefaultMaxResults(int defaultMaxResults) {
		this.defaultMaxResults = defaultMaxResults;
	}

	/**
	 * Gets the default max results.
	 *
	 * @return the default max results
	 */
	public int getDefaultMaxResults() {
		return defaultMaxResults;
	}

	/**
	 * Execute query by name single result.
	 *
	 * @param queryName the query name
	 * @return the t
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T executeQueryByNameSingleResult(String queryName) {
		return (T) executeQueryByNameSingleResult(queryName, (Object[]) null);
	}

	/**
	 * Execute query by name single result.
	 *
	 * @param queryName the query name
	 * @param parameters the parameters
	 * @return the t
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T executeQueryByNameSingleResult(String queryName,
			Object... parameters) {
		TypedQuery<T> query = createNamedQuery(queryName,
				DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
		return (T) query.getSingleResult();
	}

	/**
	 * Execute query by name.
	 *
	 * @param queryName the query name
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQueryByName(String queryName) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults());
	}

	/**
	 * Execute query by name.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQueryByName(String queryName, Integer firstResult,
			Integer maxResults) {
		return executeQueryByName(queryName, firstResult, maxResults,
				(Object[]) null);
	}

	/**
	 * Execute query by name.
	 *
	 * @param queryName the query name
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQueryByName(String queryName, Object... parameters) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults(), parameters);
	}

	/**
	 * Execute query by name.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQueryByName(String queryName, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<T> query = createNamedQuery(queryName, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}

	/**
	 * Creates the named query.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<T> createNamedQuery(String queryName,
			Integer firstResult, Integer maxResults) {
		return createNamedQuery(queryName, firstResult, maxResults,
				(Object[]) null);
	}

	/**
	 * Creates the named query.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<T> createNamedQuery(String queryName,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<T> query = getEntityManager().createNamedQuery(queryName,
				getTClass());
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX
				: firstResult);
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}

		return query;
	}

	/**
	 * Execute query.
	 *
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQuery(String queryString, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<T> query = createQuery(getTClass(), queryString,
				firstResult, maxResults, parameters);
		return query.getResultList();
	}

	/**
	 * Execute query.
	 *
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> executeQuery(String queryString, Object... parameters) {
		TypedQuery<T> query = createQuery(getTClass(), queryString,
				DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	/**
	 * Execute query single result.
	 *
	 * @param queryString the query string
	 * @return the t
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T executeQuerySingleResult(String queryString) {
		return executeQuerySingleResult(queryString, (Object[]) null);
	}

	/**
	 * Execute query single result.
	 *
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the t
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T executeQuerySingleResult(String queryString, Object... parameters) {
		TypedQuery<T> query = createQuerySingleResult(getTClass(), queryString,
				parameters);
		return query.getSingleResult();
	}

	/**
	 * Creates the query.
	 *
	 * @param resultClass the result class
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<T> createQuery(Class<T> resultClass, String queryString,
			Integer firstResult, Integer maxResults) {
		return createQuery(resultClass, queryString, firstResult, maxResults,
				(Object[]) null);
	}

	/**
	 * Creates the query single result.
	 *
	 * @param resultClass the result class
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<T> createQuerySingleResult(Class<T> resultClass,
			String queryString, Object... parameters) {
		return createQuery(resultClass, queryString,
				DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
	}

	/**
	 * Creates the query.
	 *
	 * @param resultClass the result class
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<T> createQuery(Class<T> resultClass, String queryString,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<T> query = getEntityManager().createQuery(queryString,
				resultClass);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX
				: firstResult);
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}

		return query;
	}

	// BASIC ********************************************************************************************************************
	// -------

	/**
	 * Execute query by name single result o.
	 *
	 * @param queryName the query name
	 * @return the object
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object executeQueryByNameSingleResultO(String queryName) {
		return executeQueryByNameSingleResultO(queryName, (Object[]) null);
	}

	/**
	 * Execute query by name single result o.
	 *
	 * @param queryName the query name
	 * @param parameters the parameters
	 * @return the object
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object executeQueryByNameSingleResultO(String queryName,
			Object... parameters) {
		Query query = createNamedQueryObj(queryName,
				DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
		return query.getSingleResult();
	}

	// -------

	/**
	 * Execute query by name o.
	 *
	 * @param queryName the query name
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryByNameO(String queryName) {
		return executeQueryByNameO(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults());
	}

	/**
	 * Execute query by name o.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryByNameO(String queryName,
			Integer firstResult, Integer maxResults) {
		return executeQueryByNameO(queryName, firstResult, maxResults,
				(Object[]) null);
	}

	/**
	 * Execute query by name o.
	 *
	 * @param queryName the query name
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryByNameO(String queryName,
			Object... parameters) {
		return executeQueryByNameO(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults(), parameters);
	}

	/**
	 * Execute query by name o.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryByNameO(String queryName,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = createNamedQueryObj(queryName, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}

	// --------

	/**
	 * Creates the named query obj.
	 *
	 * @param queryName the query name
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<Object> createNamedQueryObj(String queryName,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = getEntityManager().createNamedQuery(
				queryName, Object.class);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX
				: firstResult);
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}

		return query;
	}

	// -------------------------

	/**
	 * Execute query o.
	 *
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryO(String queryString, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = createQueryObj(queryString, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}

	/**
	 * Execute query o.
	 *
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the list
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object> executeQueryO(String queryString, Object... parameters) {
		TypedQuery<Object> query = createQueryObj(queryString,
				DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	/**
	 * Execute query single result o.
	 *
	 * @param queryString the query string
	 * @return the object
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object executeQuerySingleResultO(String queryString) {
		return executeQuerySingleResult(queryString, (Object[]) null);
	}

	/**
	 * Execute query single result o.
	 *
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the object
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Object executeQuerySingleResultO(String queryString,
			Object... parameters) {
		TypedQuery<Object> query = createQuerySingleResultObj(queryString,
				parameters);
		return query.getSingleResult();
	}

	/**
	 * Creates the query single result obj.
	 *
	 * @param queryString the query string
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<Object> createQuerySingleResultObj(String queryString,
			Object... parameters) {
		return createQueryObj(queryString, DEFAULT_FIRST_RESULT_INDEX, 1,
				parameters);
	}

	/**
	 * Creates the query obj.
	 *
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<Object> createQueryObj(String queryString,
			Integer firstResult, Integer maxResults) {
		return createQueryObj(queryString, firstResult, maxResults,
				(Object[]) null);
	}

	/**
	 * Creates the query obj.
	 *
	 * @param queryString the query string
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param parameters the parameters
	 * @return the typed query
	 */
	@Transactional
	public TypedQuery<Object> createQueryObj(String queryString,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = getEntityManager().createQuery(queryString,
				Object.class);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				query.setParameter(i + 1, parameters[i]);
			}
		}

		query.setFirstResult(firstResult == null || firstResult < 0 ? DEFAULT_FIRST_RESULT_INDEX
				: firstResult);
		if (maxResults != null && maxResults > 0) {
			query.setMaxResults(maxResults);
		}

		return query;
	}

	// Criteria functions
	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the criteria builder.
	 *
	 * @return the criteria builder
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CriteriaBuilder getCriteriaBuilder() throws Exception {
		return getEntityManager().getCriteriaBuilder();
	}

	/**
	 * Gets the all entities.
	 *
	 * @param cb the cb
	 * @param cq the cq
	 * @param t the t
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(CriteriaBuilder cb, CriteriaQuery<T> cq,
			Root<T> t) throws Exception {
		return getAllEntities(true, -1, -1, null, null, cb, cq, t);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @param cb the cb
	 * @param cq the cq
	 * @param t the t
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder, CriteriaBuilder cb, CriteriaQuery<T> cq,
			Root<T> t) throws Exception {
		return getAllEntities(true, -1, -1, orderByAttribute, sortOrder, cb,
				cq, t);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @param cb the cb
	 * @param cq the cq
	 * @param t the t
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder,
			CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> t)
			throws Exception {
		return getAllEntities(false, firstResult, maxResults, orderByAttribute,
				sortOrder, cb, cq, t);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param all the all
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param orderByAttribute the order by attribute
	 * @param sortOrder the sort order
	 * @param cb the cb
	 * @param cq the cq
	 * @param t the t
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder,
			CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> t)
			throws Exception {

		// order by ------------------------------------------
		if (orderByAttribute != null) {
			switch (sortOrder) {
			case DESC:
				cq.orderBy(cb.desc(t.get(orderByAttribute)));
				break;
			case ASC:
				cq.orderBy(cb.asc(t.get(orderByAttribute)));
				break;
			default:
				cq.orderBy(cb.asc(t.get(orderByAttribute)));
				break;
			}
		}
		return getAllEntities(all, firstResult, maxResults, cq);
	}

	/**
	 * Gets the all entities.
	 *
	 * @param all the all
	 * @param firstResult the first result
	 * @param maxResults the max results
	 * @param cq the cq
	 * @return the all entities
	 * @throws Exception the exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getAllEntities(boolean all, int firstResult, int maxResults,
			CriteriaQuery<T> cq) throws Exception {

		// create query
		// ----------------------------------------------------------------------------
		TypedQuery<T> q = getEntityManager().createQuery(cq);

		if (!all) {
			q.setMaxResults(maxResults);
			System.out.println(">>>firstResult = " + firstResult);
			q.setFirstResult(firstResult >= 0 ? firstResult : 0);
		}
		return q.getResultList();
	}

	// Single result
	// ------------------------------------------------------------------

	

}
