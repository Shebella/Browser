package tw.org.itri.ccma.css.safebox.model;

import org.apache.commons.lang.StringUtils;

/***
 * 分頁資訊
 * 
 * @author A10138
 * 
 */
public class PagingInfo {
	private int maxPageSize = 0;

	private int totalCounts = 0; // resultBeanList.size();
	private int currentPage = 1;
	private int totalPages = 1;
	private int pageSize = 0; // getDefaultPageSize();
	private int pageStart = 0;
	private int pageEnd = 0;

	public static PagingInfo getInstance(int total_count, int max_page_size) {
		return new PagingInfo(total_count, max_page_size);
	}

	public PagingInfo(int total_count, int max_page_size) {
		totalCounts = total_count;
		maxPageSize = max_page_size;
		pageEnd = totalCounts;
	}

	public void calculatePaging(String cur_page, String page_size) {
		int currentPage = 1;
		int pageSize = maxPageSize;
		if (StringUtils.isNotEmpty(cur_page)) {
			try {
				currentPage = Integer.valueOf(cur_page);
				if (StringUtils.isNotEmpty(page_size)) {
					pageSize = Integer.valueOf(page_size);
				}
			} catch (Exception e) {
			}
		}

		calculatePaging(currentPage, pageSize);
	}

	public void calculatePaging(int cur_page, int page_size) {
		if (0 < totalCounts) {
			currentPage = cur_page;

			pageSize = page_size;

			if (0 >= pageSize || pageSize > maxPageSize) {
				pageSize = maxPageSize;
			}

			totalPages = totalCounts / pageSize;
			if (0 >= totalPages) {
				totalPages = 1;
			} else {
				totalPages = (0 < (totalCounts % pageSize)) ? totalPages + 1 : totalPages;
			}

			if (currentPage > totalPages || currentPage <= 0) {
				currentPage = 1;
			} else {
				pageStart = (currentPage * pageSize) - pageSize;
			}

			if (0 > pageStart || totalCounts <= pageStart) {
				pageStart = 0;
			}

			if (pageStart + pageSize < totalCounts) {
				pageEnd = pageStart + pageSize;
			}
		}
	}

	public int getTotalCounts() {
		return totalCounts;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageStart() {
		return pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

}
