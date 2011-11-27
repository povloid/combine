/**
 * 
 */
package pk.home.libs.combine.basic;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * 
 * Интерфейс, описывающи функционал работы с деревом
 * 
 * 
 * @author traveler
 *
 */
public interface TreeFunctional <T extends Object>{
		
	/**
	 * Получить количество дочерних объектов
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	long getChildrensCount(T parent) throws Exception;
	
	
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Получить список дочерних объектов
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	List<T> getChildrens(T parent) throws Exception;
	
	
	/**
	 * Получить список дочерних объектов cс сортировкой
	 * @param parent
	 * @param orderByAttribute
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	List<T> getChildrens(T parent, SingularAttribute<T, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception;
	
	
	/**
	 * Получить список дочерних объектов - порционно
	 * @param parent
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	List<T> getChildrens(T parent, int firstResult, int maxResults) throws Exception;
	
	/**
	 * Получить список дочерних объектов - порционно и сортируя
	 * @param parent
	 * @param all
	 * @param firstResult
	 * @param maxResults
	 * @param orderByAttribute
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	List<T> getChildrens(T parent, boolean all, int firstResult, int maxResults,
			SingularAttribute<T, ?> orderByAttribute, SortOrderType sortOrder) throws Exception;
	
	
	// ----------------------------------------------------------------------------------------------------------------
	
	/**
	 * Установить нового родителя
	 * @param object
	 * @param parent
	 * @throws Exception
	 */
	void setParent(T object, T parent) throws Exception;


	
	
	
	

}
