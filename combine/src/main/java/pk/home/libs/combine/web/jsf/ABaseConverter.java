package pk.home.libs.combine.web.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;


/**
 * 
 * Class ABaseConverter
 *
 * @author Kopychenko Pavel
 *
 * @date Jun 28, 2013
 *
 */
@Component
public abstract class ABaseConverter implements javax.faces.convert.Converter{
	


	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
            	
                return findObject(submittedValue);
                
            } catch(Exception exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid"));
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            try {
				return getStringKey(value);
            } catch(Exception exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid"));
            }
        }
    }
    
    
    /**
     * Find object.
     *
     * @param key the key
     * @return the object
     * @throws Exception the exception
     */
    protected abstract Object findObject(String key) throws Exception;
    
    
    /**
     * Gets the string key.
     *
     * @param value the value
     * @return the string key
     * @throws Exception the exception
     */
    protected abstract String getStringKey(Object value) throws Exception;
    
}