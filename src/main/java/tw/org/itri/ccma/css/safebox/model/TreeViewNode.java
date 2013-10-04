package tw.org.itri.ccma.css.safebox.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/***
 * 前端樹狀功能使用的節點資訊
 * 
 * @author A10138
 * 
 */
public class TreeViewNode {
	private String title;
	private String key;

	@JsonProperty("children")
	private List<TreeViewNode> subNodeList;

	@JsonProperty("isLazy")
	private boolean isLazy;

	@JsonProperty("isFolder")
	private boolean isFolder;

	private boolean expand;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isLazy() {
		return isLazy;
	}

	public void setLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public List<TreeViewNode> getSubNodeList() {
		return subNodeList;
	}

	public void setSubNodeList(List<TreeViewNode> subNodeList) {
		this.subNodeList = subNodeList;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

}
