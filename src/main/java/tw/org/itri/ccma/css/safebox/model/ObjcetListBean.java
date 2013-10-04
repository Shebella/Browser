package tw.org.itri.ccma.css.safebox.model;

import java.util.List;

/***
 * 給 Mobile Client 用的檔案清單資訊，會包含分頁資訊
 * 
 * @author A10138
 * 
 */
public class ObjcetListBean {
	private PagingInfo pagingInfo;
	private List<ObjectBean> objBeanList;

	public static ObjcetListBean getInstance(PagingInfo paging_info, List<ObjectBean> objbean_list) {
		ObjcetListBean objListBean = new ObjcetListBean();
		objListBean.setPagingInfo(paging_info);
		objListBean.setObjBeanList(objbean_list);

		return objListBean;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public List<ObjectBean> getObjBeanList() {
		return objBeanList;
	}

	public void setObjBeanList(List<ObjectBean> objBeanList) {
		this.objBeanList = objBeanList;
	}

}
