package pk.home.libs.combine.web.jsf.flow.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public abstract class WFLazyDataModel<T> extends LazyDataModel<T> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3468218369857397831L;

	public abstract T getRowData(String rowKey);

	public abstract Object getRowKey(T object);

	@SuppressWarnings("unchecked")
	protected List<T> getDataList() {
		return (List<T>) getWrappedData();
	}

	@Override
	public List<T> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			// rowCount
			int dataSize;

			dataSize = count(filters);

			this.setRowCount(dataSize);

			// paginate
			if (dataSize > pageSize) {
				try {
					return aload(first, first + pageSize, sortField, sortOrder,
							filters);
				} catch (IndexOutOfBoundsException e) {

					return aload(first, first + (dataSize % pageSize),
							sortField, sortOrder, filters);
				}
			} else {
				return aload(first, pageSize, sortField, sortOrder, filters);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return null;
	}

	/**
	 * Получить общий размер в базе
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract int count(Map<String, String> filters) throws Exception;

	/**
	 * Загрузить порцию записей
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	protected abstract List<T> aload(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) throws Exception;

	//@Override
	//public void setRowIndex(int rowIndex) {
	//	super.setRowIndex(rowIndex == 0 ? -1 : rowIndex);
	//}
	
	
	
	
}
