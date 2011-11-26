/**
 * 
 */
package pk.home.libs.combine.basic;

import java.util.List;

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
	
	/**
	 * Получить список дочерних объектов
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	List<T> getChildrens(T parent) throws Exception;
	
	/**
	 * Установить нового родителя
	 * @param object
	 * @param parent
	 * @throws Exception
	 */
	void setParent(T object, T parent) throws Exception;
	
	
	

}
