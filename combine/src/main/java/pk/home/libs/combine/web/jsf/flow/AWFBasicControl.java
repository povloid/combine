package pk.home.libs.combine.web.jsf.flow;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class AWFBasicControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4752053517338554593L;

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
	 * Cancel action
	 * @return
	 */
	public String cancel(){
		return "cancel";
	}

}
