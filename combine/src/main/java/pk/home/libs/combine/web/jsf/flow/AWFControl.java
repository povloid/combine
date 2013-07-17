package pk.home.libs.combine.web.jsf.flow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 * The basic for webflow dialog system
 * 
 * Mast be Serializable !!!
 * 
 * @author povloid
 * 
 */
public abstract class AWFControl<T extends Object, K extends Object> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected T edited;

	/**
	 * Find Entity for key
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract T findEdited(K id) throws Exception;

	/**
	 * Create new Entity
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract T newEdited() throws Exception;

	/**
	 * initialize lazy function
	 */
	public void init() {
		try {
			init0();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * initialize
	 * 
	 * @throws Exception
	 */
	protected void init0() throws Exception {

	}

	/**
	 * Find bean for name
	 * 
	 * @param name
	 * @return
	 */
	public Object findBean(String name) {
		ApplicationContext ctx = FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance());
		return ctx.getBean(name);
	}
	
	/**
	 * Find bean for name.
	 *
	 * @param <Z> the generic type
	 * @param name the name
	 * @param requiredType the required type
	 * @return the z
	 */
	public <Z> Z findBean(String name, Class<Z> requiredType) {
		ApplicationContext ctx = FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance());
		return (Z) ctx.getBean(name, requiredType);
	}
	

	// actions
	// ---------------------------------------------------------------------------------------------------------
	public static final String ERROR = "error";

	public static final String ADD_COMPLITE = "addComplite";

	/**
	 * Добавить новую запись
	 * 
	 * @return
	 */
	public String confirmAdd() {
		try {

			confirmAddImpl();

		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return ERROR;

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			return ERROR;
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok: ",
						"Запись успешно добавлена"));

		return ADD_COMPLITE;
	}

	/**
	 * add action implimintation
	 * 
	 * @throws Exception
	 */
	protected abstract void confirmAddImpl() throws Exception;

	public static final String EDIT_COMPLITE = "editComplite";

	/**
	 * 
	 * Обновить запись
	 * 
	 * @return
	 */
	public String confirmEdit() {
		try {
			confirmEditImpl();
		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return ERROR;

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			return ERROR;
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok: ",
						"Запись успешно обновлена"));

		return EDIT_COMPLITE;
	}

	public static final String EDIT_AND_CLOSE_COMPLITE = "editCompliteAndClose";

	/**
	 * Подтвердить и закрыть диалог
	 * 
	 * @return
	 */
	public String confirmEditAndClose() {
		String result = confirmEdit();

		if (result.equals(ERROR))
			return ERROR;

		return EDIT_AND_CLOSE_COMPLITE;
	}

	/**
	 * edit action implimintation
	 * 
	 * @throws Exception
	 */
	protected abstract void confirmEditImpl() throws Exception;

	public static final String DEL_COMPLITE = "delComplite";

	/**
	 * Удалить запись
	 * 
	 * @return
	 * @throws Exception
	 */
	public String confirmDel() throws Exception {
		try {
			confirmDelImpl();
		} catch (javax.persistence.OptimisticLockException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: ",
									"Версия устарела, кто-то уже обновил данную запись. Закройте диалог."));
			return ERROR;

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			return ERROR;
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Ok: ",
						"Запись успешно удалена"));

		return DEL_COMPLITE;
	}

	/**
	 * del action implimintation
	 * 
	 * @throws Exception
	 */
	protected abstract void confirmDelImpl() throws Exception;

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public T getEdited() {
		return edited;
	}

	public void setEdited(T edited) {
		this.edited = edited;
	}

}
