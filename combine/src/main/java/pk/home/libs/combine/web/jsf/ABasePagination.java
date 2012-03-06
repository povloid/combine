package pk.home.libs.combine.web.jsf;

import java.io.Serializable;
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
public abstract class ABasePagination<T extends Object> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4266659681520981975L;

	private String ppage; // страница
	private String prows; // число записей на странице

	private int page = 1;
	private int rows = 10;
	private int pages;

	// calculate bean params
	private int allPagesCount;
	private long allRowsCount;

	public static final int maxOPButtons = 10;
	private List<OrderingPaginationButton> oPButtons;

	private List<T> result = new ArrayList<T>();

	// -------------------------------------------------------------------------------------------------

	private void parseParams() throws Exception {
		page = ppage != null && ppage.length() > 0 ? Integer.parseInt(ppage
				.trim()) : page;
		ppage = null;

		rows = prows != null && prows.length() > 0 ? Integer.parseInt(prows
				.trim()) : rows;
		prows = null;
	}

	// calculate pages
	// ------------------------------------------------------------------------------------------------
	/**
	 * Calculate beans params
	 */
	private void calculatePagination() {
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

	/**
	 * get started position
	 * 
	 * @return
	 */
	protected int getFirstResult() {
		return (page - 1) * rows;
	}

	protected int getMaxResults() {
		return rows;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// INIT

	/**
	 * View initialisation
	 */
	public void init() {
		try {
			parseParams();

			aInit();

			result.clear();
			allRowsCount = 0;
			AResult<T> ar = executeQuery();
			if (ar != null && ar.allRowsCount > 0 && ar.result != null
					&& ar.result.size() > 0) {
				allRowsCount = ar.allRowsCount;
				result = ar.result;
			}

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

	/**
	 * iside returning result class
	 * 
	 * @author povloid
	 * 
	 */
	protected class AResult<E> {
		private int allRowsCount;
		private List<E> result;

		public AResult(int allRowsCount, List<E> result) {
			super();
			this.allRowsCount = allRowsCount;
			this.result = result;
		}

		public int getAllRowsCount() {
			return allRowsCount;
		}

		public void setAllRowsCount(int allRowsCount) {
			this.allRowsCount = allRowsCount;
		}

		public List<E> getResult() {
			return result;
		}

		public void setResult(List<E> result) {
			this.result = result;
		}
	}

	protected abstract AResult<T> executeQuery() throws Exception;

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

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

}
