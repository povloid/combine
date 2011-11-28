package pk.home.libs.combine.dao;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

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

	private int defaultMaxResults = DEFAULT_MAX_RESULTS;

	/**
	 * The {@link EntityManager} which is used by all query manipulation and
	 * execution in this DAO.
	 * 
	 * @return the {@link EntityManager}
	 */
	public abstract EntityManager getEntityManager();

	/**
	 * Получить тип
	 * 
	 * @return
	 */
	protected abstract Class<T> getTClass();

	/**
	 * Получить первичный ключ
	 * 
	 * @param o
	 * @return
	 */
	public abstract Object getPrimaryKey(T o);

	public enum SortOrderType {
		ASC, DESC
	}

	@Transactional
	public List<T> getAllEntities() throws Exception {
		return getAllEntities(true, -1, -1, null, SortOrderType.ASC);
	}

	@Transactional
	public List<T> getAllEntities(SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {
		return getAllEntities(true, -1, -1, orderByAttribute, sortOrder);
	}

	@Transactional
	public List<T> getAllEntities(int firstResult, int maxResults)
			throws Exception {
		return getAllEntities(false, firstResult, maxResults, null,
				SortOrderType.ASC);
	}

	@Transactional
	public List<T> getAllEntities(int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {
		return getAllEntities(false, firstResult, maxResults, orderByAttribute,
				sortOrder);
	}

	@Transactional
	public List<T> getAllEntities(boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)  throws Exception {

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); // Только
																		// так
																		// заработало
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
			System.out.println(">>>firstResult = " + firstResult);
			q.setFirstResult(firstResult >= 0? firstResult : 0);
		}
		return q.getResultList();
	}

	@Transactional
	public T find(Object key) throws Exception {
		if (key == null) {
			return null;
		} else {
			return getEntityManager().find(getTClass(), key);
		}
	}

	@Transactional
	public T getManagedEntity(T unmanagedBean) throws Exception {
		return getEntityManager().find(getTClass(),
				getPrimaryKey(unmanagedBean));
	}

	@Transactional
	public long count() throws Exception {
		CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<T> rt = cq.from(getTClass());
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).longValue();
	}

	// -------------------------------------------------------------------------------------------------------------

	/**
	 * Add new o
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public T persist(T o) throws Exception {
		System.out.print(">>>PERSIST: " + o.toString());
		getEntityManager().persist(o);
		return o;
	}

	/**
	 * Refresh the deatached bean
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public T refresh(T o) throws Exception {
		getEntityManager().refresh(o);
		return o;
	}

	/**
	 * The update bean
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public T merge(T o) throws Exception {
		o = getEntityManager().merge(o);
		return o;
	}

	/**
	 * Delete bean
	 * 
	 * @param section
	 * @throws Exception
	 */
	@Transactional
	public void remove(T o) throws Exception {
		getEntityManager().remove(o);
		getEntityManager().flush();
	}

	// ---------------------------------------------------------------------------------

	@Transactional
	public void setDefaultMaxResults(int defaultMaxResults) {
		this.defaultMaxResults = defaultMaxResults;
	}

	@Transactional
	public int getDefaultMaxResults() {
		return defaultMaxResults;
	}

	@Transactional
	public T executeQueryByNameSingleResult(String queryName) {
		return (T) executeQueryByNameSingleResult(queryName, (Object[]) null);
	}

	@Transactional
	public T executeQueryByNameSingleResult(String queryName,
			Object... parameters) {
		TypedQuery<T> query = createNamedQuery(queryName,
				DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
		return (T) query.getSingleResult();
	}

	@Transactional
	public List<T> executeQueryByName(String queryName) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults());
	}

	@Transactional
	public List<T> executeQueryByName(String queryName, Integer firstResult,
			Integer maxResults) {
		return executeQueryByName(queryName, firstResult, maxResults,
				(Object[]) null);
	}

	@Transactional
	public List<T> executeQueryByName(String queryName, Object... parameters) {
		return executeQueryByName(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults(), parameters);
	}

	@Transactional
	public List<T> executeQueryByName(String queryName, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<T> query = createNamedQuery(queryName, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}

	@Transactional
	public TypedQuery<T> createNamedQuery(String queryName,
			Integer firstResult, Integer maxResults) {
		return createNamedQuery(queryName, firstResult, maxResults,
				(Object[]) null);
	}

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

	@Transactional
	public List<T> executeQuery(String queryString, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<T> query = createQuery(getTClass(), queryString,
				firstResult, maxResults, parameters);
		return query.getResultList();
	}

	@Transactional
	public List<T> executeQuery(String queryString, Object... parameters) {
		TypedQuery<T> query = createQuery(getTClass(), queryString,
				DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	@Transactional
	public T executeQuerySingleResult(String queryString) {
		return executeQuerySingleResult(queryString, (Object[]) null);
	}

	@Transactional
	public T executeQuerySingleResult(String queryString, Object... parameters) {
		TypedQuery<T> query = createQuerySingleResult(getTClass(), queryString,
				parameters);
		return query.getSingleResult();
	}

	@Transactional
	public TypedQuery<T> createQuery(Class<T> resultClass, String queryString,
			Integer firstResult, Integer maxResults) {
		return createQuery(resultClass, queryString, firstResult, maxResults,
				(Object[]) null);
	}

	@Transactional
	public TypedQuery<T> createQuerySingleResult(Class<T> resultClass,
			String queryString, Object... parameters) {
		return createQuery(resultClass, queryString,
				DEFAULT_FIRST_RESULT_INDEX, 1, parameters);
	}

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

	// BASIC *****************************************************************

	// -------
	
	@Transactional
	public Object executeQueryByNameSingleResultO(String queryName) {
		return executeQueryByNameSingleResultO(queryName, (Object[]) null);
	}

	@Transactional
	public Object executeQueryByNameSingleResultO(String queryName,
			Object... parameters) {
		Query query = createNamedQueryObj(queryName, DEFAULT_FIRST_RESULT_INDEX,
				1, parameters);
		return query.getSingleResult();
	}

	
	// -------
	
	@Transactional
	public List<Object> executeQueryByNameO(String queryName) {
		return executeQueryByNameO(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults());
	}

	@Transactional
	public List<Object> executeQueryByNameO(String queryName,
			Integer firstResult, Integer maxResults) {
		return executeQueryByNameO(queryName, firstResult, maxResults,
				(Object[]) null);
	}

	@Transactional
	public List<Object> executeQueryByNameO(String queryName,
			Object... parameters) {
		return executeQueryByNameO(queryName, DEFAULT_FIRST_RESULT_INDEX,
				getDefaultMaxResults(), parameters);
	}

	@Transactional
	public List<Object> executeQueryByNameO(String queryName,
			Integer firstResult, Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = createNamedQueryObj(queryName, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}
	
	// --------
	

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

	@Transactional
	public List<Object> executeQueryO(String queryString, Integer firstResult,
			Integer maxResults, Object... parameters) {
		TypedQuery<Object> query = createQueryObj(queryString, firstResult,
				maxResults, parameters);
		return query.getResultList();
	}

	@Transactional
	public List<Object> executeQueryO(String queryString, Object... parameters) {
		TypedQuery<Object> query = createQueryObj(queryString,
				DEFAULT_FIRST_RESULT_INDEX, getDefaultMaxResults(), parameters);
		return query.getResultList();
	}

	@Transactional
	public Object executeQuerySingleResultO(String queryString) {
		return executeQuerySingleResult(queryString, (Object[]) null);
	}

	@Transactional
	public Object executeQuerySingleResultO(String queryString,
			Object... parameters) {
		TypedQuery<Object> query = createQuerySingleResultObj(queryString,
				parameters);
		return query.getSingleResult();
	}

	@Transactional
	public TypedQuery<Object> createQuerySingleResultObj(String queryString,
			Object... parameters) {
		return createQueryObj(queryString, DEFAULT_FIRST_RESULT_INDEX, 1,
				parameters);
	}

	@Transactional
	public TypedQuery<Object> createQueryObj(String queryString,
			Integer firstResult, Integer maxResults) {
		return createQueryObj(queryString, firstResult, maxResults,
				(Object[]) null);
	}

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

}
