package pk.home.libs.combine.html;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

public final class HTMLUtils {

	/**
	 * Отчистить текст от html разметки
	 * 
	 * @param html
	 * @return
	 */
	public static String cleanText(String html) {
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		props.setOmitDeprecatedTags(true);

		String s = (new HtmlCleaner(props))
				.clean(html.replace("</div>", "&nbsp;</div>").replace("<div>",
						"<div>&nbsp;")).getText().toString();
		return s;
	}

}
