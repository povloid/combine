package pk.home.libs.combine.web.jsf;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class ABaseTreeDialog<T> extends ABaseDialog<T> {

	protected String parentSKey;

	private T parent;
	
	/**
	 * Before add mode any actions
	 */
	@Override
	protected void afterAddMakeMode() throws Exception {
		if (getParent(edited) == null && parentSKey != null
				&& parentSKey.trim().length() > 0) {
			parent = findParentObject(parentSKey);
			System.out.println(">>>>parent=" + parent);
			setParent(edited, parent);
		}
	}
	
	@Override
	protected void afterAnyConfirmAction() throws Exception{
		parent = null;
		parentSKey = null;
	}

	/**
	 * Find the parent from parentSKey parametr
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract T findParentObject(String parentSKey) throws Exception;

	/**
	 * Set the parent
	 * 
	 * @param selected
	 * @param parent
	 * @throws Exception
	 */
	protected abstract void setParent(T edited, T parent) throws Exception;

	protected abstract T getParent(T edited) throws Exception;

	// ---------------------------------------------------------------------------------------------

	// example: cancel2=sKey=108,sKey=105
	private String cancel2;
	

	

	@Override
	protected void redirect() throws IOException {
		redirect(true);
	}

	protected void redirect(boolean conform) throws IOException {
		if (redirect_plus != null && redirect_plus.equals(REDIRECT_YES)
				&& retUrl != null)

			if (!conform && cancel2 != null) {
				String c[] = cancel2.split(",");
				retUrl = retUrl.replace(c[0], c[1]);
			}

		FacesContext.getCurrentInstance().getExternalContext().redirect(retUrl);

		cancel2 = null;
	}

	@Override
	public String cancel() {
		String url = "";
		try {
			parent = null;
			parentSKey = null;
			redirect(false);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
		return url;
	}

	// getters and setters
	// --------------------------------------------------------------------------------------------

	public String getParentSKey() {
		return parentSKey;
	}

	public void setParentSKey(String parentSKey) {
		this.parentSKey = parentSKey;
	}

	public String getCancel2() {
		return cancel2;
	}

	public void setCancel2(String cancel2) {
		this.cancel2 = cancel2;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	
	
}
