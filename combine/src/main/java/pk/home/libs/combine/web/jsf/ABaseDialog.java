package pk.home.libs.combine.web.jsf;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author povloid
 * 
 * @param <T>
 */
public abstract class ABaseDialog<T extends Object>  implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -17495380623375031L;

	/**
	 * Edited bean
	 */
	protected T edited;

	// Request params
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Selected bean
	 */
	protected String sKey;

	/**
	 * Return URL
	 */
	protected String retUrl;

	/**
	 * retUrl + "?faces-redirect=true";
	 * 
	 */
	protected String redirect_plus;
	protected final static String REDIRECT_PLUS = "&faces-redirect=true";
	protected final static String REDIRECT_YES = "yes";
	protected final static String REDIRECT_NO = "no";

	/**
	 * Dialog mode - add,edit,delete
	 */
	protected String mode;

	protected final static String MODE_ADD = "add";
	protected final static String MODE_EDIT = "edit";
	protected final static String MODE_DEL = "del";

	/**
	 * Will implimented create new edited object
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract T createNewEditedObject() throws Exception;

	/**
	 * will implimented find selected object
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract T findEditedObject(String sKey) throws Exception;

	// ----------------------------------------------------------------------------------------------------------------
	// INIT

	/**
	 * View initialisation
	 */
	public void init() {
		try {
			makeMode();
			aInit();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * View initialisation impl
	 * 
	 * @throws Exception
	 */
	protected abstract void aInit() throws Exception;

	// functional
	// ---------------------------------------------------------------------------------------------------------

	// protected String actionRetUrl() {
	// String tretUrl = this.retUrl != null ? this.retUrl : "";
	//
	// if (redirect_plus != null) {
	// if (tretUrl.equals(redirect_plus.equals(REDIRECT_YES))) {
	//
	// if (tretUrl.charAt(tretUrl.length() - 1) == '?')
	// tretUrl += REDIRECT_PLUS;
	// else
	// tretUrl += "?" + REDIRECT_PLUS;
	// }
	// }
	//
	// return tretUrl;
	// }

	/**
	 * Redirect to retUrl
	 * 
	 * @throws IOException
	 */
	protected void redirect() throws IOException {
		if (redirect_plus != null && redirect_plus.equals(REDIRECT_YES)
				&& retUrl != null)
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(retUrl);
	}

	private T lastCreatedObject;

	/**
	 * Prepere mode actions
	 * 
	 * @throws Exception
	 */
	protected void makeMode() throws Exception {

		if (mode == null) {
			throw new Exception("Error: Parametr mode is null!");
		}

		if (mode.equals(MODE_ADD)) {
			beforeAddMakeMode();
			if (this.lastCreatedObject == null
					|| this.lastCreatedObject != this.edited)
				this.edited = createNewEditedObject();

			this.lastCreatedObject = this.edited;
			prepareAdd();
			afterAddMakeMode();
		} else if ((sKey != null) && mode.equals(MODE_EDIT)) {
			beforeEditMakeMode();
			this.edited = findEditedObject(this.sKey);
			prepareEdit();
			afterEditMakeMode();
		} else if (sKey != null && mode.equals(MODE_DEL)) {
			beforeDelMakeMode();
			this.edited = findEditedObject(this.sKey);
			prepareDel();
			afterDelMakeMode();
		}

		sKey = null;

	}

	/**
	 * Before add mode any actions
	 * 
	 * @throws Exception
	 */
	protected void beforeDelMakeMode() throws Exception {

	}

	/**
	 * Before add mode any actions
	 * 
	 * @throws Exception
	 */
	protected void beforeEditMakeMode() throws Exception {

	}

	/**
	 * Before add mode any actions
	 * 
	 * @throws Exception
	 */
	protected void beforeAddMakeMode() throws Exception {

	}

	// -------

	/**
	 * after add mode any actions
	 * 
	 * @throws Exception
	 */
	protected void afterDelMakeMode() throws Exception {
	}

	/**
	 * after edit mode any actions
	 * 
	 * @throws Exception
	 */
	protected void afterEditMakeMode() throws Exception {
	}

	/**
	 * after del mode any actions
	 * 
	 * @throws Exception
	 */
	protected void afterAddMakeMode() throws Exception {
	}

	// Actions
	// ---------------------------------------------------------------------------------------------------------

	public String cancel() {
		String url = "";
		try {
			lastCreatedObject = null;
			redirect();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
		return url;
	}

	// ADD
	// -------------------------------------------------------------------------

	/**
	 * Action Add new bean
	 * 
	 * @return
	 */
	public void prepareAdd() {
		try {
			prepareAddImpl();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * Add new bean impl
	 * 
	 * @throws Exception
	 */
	protected abstract void prepareAddImpl() throws Exception;

	// ---------------------------------------------------

	/**
	 * Apply action, return the current page
	 * 
	 * @return
	 */
	public String apply() {
		if (this.edited == null) {
			return "";
		}

		try {
			aApplyImpl();
			lastCreatedObject = null;
			mode = MODE_EDIT;
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return null;
	}

	/**
	 * Применить
	 * 
	 * @throws Exception
	 */
	protected void aApplyImpl() throws Exception {

	}

	// ---------------------------------------------------

	/**
	 * Action Conform add and return
	 * 
	 * @return
	 */
	public String confirmAdd() {
		if (this.edited == null) {
			return "";
		}

		String url = "";
		try {
			url = confirmAddImpl();
			lastCreatedObject = null;
			afterAnyConfirmAction();
			redirect();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return url;
	}

	/**
	 * Conform add and return impl
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract String confirmAddImpl() throws Exception;

	// EDIT
	// ----------------------------------------------------------------------------------------

	/**
	 * Action Edit bean
	 */
	public void prepareEdit() {
		try {
			prepareEditImpl();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * Edit bean impl
	 * 
	 * @throws Exception
	 */
	protected abstract void prepareEditImpl() throws Exception;

	// ---------------------------------------------------

	/**
	 * Action Conform edit bean and return
	 * 
	 * @return
	 */
	public String confirmEdit() {
		if (this.edited == null) {
			return "";
		}

		String url = "";
		try {
			url = confirmEditImpl();
			afterAnyConfirmAction();

			redirect();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return url;
	}

	/**
	 * Conform edit bean and return impl
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract String confirmEditImpl() throws Exception;

	// DEL
	// -----------------------------------------------------------------------------------

	/**
	 * Action Delete bean
	 */
	public void prepareDel() {
		try {
			prepareDelImpl();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * Delete bean impl
	 * 
	 * @throws Exception
	 */
	protected abstract void prepareDelImpl() throws Exception;

	/**
	 * Action Conform delete bean end return
	 * 
	 * @return
	 */
	public String confirmDel() {
		if (this.edited == null) {
			return "";
		}

		String url = "";
		try {
			url = confirmDelImpl();
			afterAnyConfirmAction();
			redirect();

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return url;
	}

	/**
	 * Conform delete bean end return impl
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract String confirmDelImpl() throws Exception;

	// --------------------------------------------------------------

	/**
	 * After any confirmed action
	 * 
	 * @throws Exception
	 */
	protected void afterAnyConfirmAction() throws Exception {

	}

	// -----------------------------------------------------------------------------------------------------------------
	// getters and setters

	/**
	 * Is mode add?
	 * 
	 * @return
	 */
	public boolean isAdd() {
		return mode.equals(MODE_ADD);
	}

	/**
	 * Is mode edit?
	 * 
	 * @return
	 */
	public boolean isEdit() {
		return mode.equals(MODE_EDIT);
	}

	/**
	 * Is mode del?
	 * 
	 * @return
	 */
	public boolean isDel() {
		return mode.equals(MODE_DEL);
	}

	public T getEdited() {
		return edited;
	}

	public void setEdited(T edited) {
		this.edited = edited;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		System.out.println(">>>retUrl: " + retUrl);

		this.retUrl = retUrl;
	}

	public String getsKey() {
		return sKey;
	}

	public void setsKey(String sKey) {
		this.sKey = sKey;
	}

	public String getRedirect_plus() {
		return redirect_plus;
	}

	public void setRedirect_plus(String redirect_plus) {
		this.redirect_plus = redirect_plus;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
