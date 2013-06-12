/**
 * 
 */
package pk.home.libs.combine.basic;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * Интерфейс, описывающий функционал работы с деревом.
 * 
 * @param <T> the generic type
 * @author Kopychenko Pavel
 * @date Jun 12, 2012
 */
public interface TreeFunctional<T extends Object> {

	/**
	 * Установить нового родителя.
	 * 
	 * @param object
	 *            the object
	 * @param parent
	 *            the parent
	 * @throws Exception
	 *             the exception
	 */
	void setParent(T object, T parent) throws Exception;

	/**
	 * Gets the childrens.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
	 * @return the childrens
	 * @throws Exception
	 *             the exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_)
			throws Exception;

	/**
	 * Gets the childrens.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
	 * @param orderByAttribute
	 *            the order by attribute
	 * @param sortOrder
	 *            the sort order
	 * @return the childrens
	 * @throws Exception
	 *             the exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception;

	/**
	 * Gets the childrens.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @return the childrens
	 * @throws Exception
	 *             the exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults) throws Exception;

	/**
	 * Gets the childrens.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @param orderByAttribute
	 *            the order by attribute
	 * @param sortOrder
	 *            the sort order
	 * @return the childrens
	 * @throws Exception
	 *             the exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception;

	/**
	 * Gets the childrens.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
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
	 * @return the childrens
	 * @throws Exception
	 *             the exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> parent_,
			boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception;

	/**
	 * Gets the childrens count.
	 * 
	 * @param parent
	 *            the parent
	 * @param parent_
	 *            the parent_
	 * @return the childrens count
	 * @throws Exception
	 *             the exception
	 */
	long getChildrensCount(T parent, SingularAttribute<T, ?> parent_)
			throws Exception;

}
