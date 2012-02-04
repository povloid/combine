package pk.home.libs.combine.web.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Базовый класс для постраничной выдачи результатов
 * 
 * @author povloid
 * 
 */
public abstract class ABasePagination {

	protected String ppage; // страница
	protected String prows; // число записей на странице

	protected int page = 1;
	protected int rows = 10;
	protected int pages;

	// calculate bean params
	protected int allPagesCount;
	protected long allRowsCount;

	public static final int maxOPButtons = 10;
	protected List<OrderingPaginationButton> oPButtons;

	// calculate pages
	// ------------------------------------------------------------------------------------------------
	/**
	 * Calculate beans params
	 */
	protected void calculatePagination() {
		if (rows == 0) {
			allPagesCount = 0;
			oPButtons = null;
		} else {
			
			// Коррекция должна быть после всех сортировок и фильтраций
			allPagesCount = (int) (allRowsCount / rows);
			allPagesCount = allRowsCount > 0 && (allRowsCount % rows) > 0 ? allPagesCount + 1
					: allPagesCount;

			// Защита при уменьшении количества страниц, например при увеличении
			// строк на страницу
			page = page > allPagesCount ? allPagesCount : page;

			// далее вормирование набора кнопок для пэйджера
			int part = maxOPButtons / 2;
			int ibegin = page - part < 1 ? 1 : page - part;
			int iend = page + part > allPagesCount ? allPagesCount : page
					+ part;

			oPButtons = new ArrayList<OrderingPaginationButton>();
			for (int i = ibegin; i < iend + 1; i++) {
				oPButtons.add(new OrderingPaginationButton(i + "", i + ""));
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	// INIT

	/**
	 * View initialisation
	 */
	public void init() {
		try {
			aInit();
	
			calculatePagination();
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

	// get's and set's
	// -------------------------------------------------------------------------------------------------
	public String getPpage() {
		return ppage;
	}

	public void setPpage(String ppage) {
		this.ppage = ppage;
	}

	public String getProws() {
		return prows;
	}

	public void setProws(String prows) {
		this.prows = prows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getAllPagesCount() {
		return allPagesCount;
	}

	public void setAllPagesCount(int allPagesCount) {
		this.allPagesCount = allPagesCount;
	}

	public long getAllRowsCount() {
		return allRowsCount;
	}

	public void setAllRowsCount(long allRowsCount) {
		this.allRowsCount = allRowsCount;
	}

	public List<OrderingPaginationButton> getoPButtons() {
		return oPButtons;
	}

	public void setoPButtons(List<OrderingPaginationButton> oPButtons) {
		this.oPButtons = oPButtons;
	}

}
