package tw.org.itri.ccma.css.safebox.config;

/***
 * 前端首頁顯示用相關資源設定
 * 
 * @author A10138
 * 
 */
public class AccountConfig {

	private String servicePath;

	private String safeboxSiteLink;
	private String downloadLink;
	private String downloadLinkWindows;
	private String downloadLinkLinux;
	private String faqLink;

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public String getSafeboxSiteLink() {
		return safeboxSiteLink;
	}

	public void setSafeboxSiteLink(String safeboxSiteLink) {
		this.safeboxSiteLink = safeboxSiteLink;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public String getDownloadLinkWindows() {
		return this.downloadLinkWindows;
	}

	public void setDownloadLinkWindows(String downloadLinkWindows) {
		this.downloadLinkWindows = downloadLinkWindows;
	}

	public String getDownloadLinkLinux() {
		return this.downloadLinkLinux;
	}

	public void setDownloadLinkLinux(String downloadLinkLinux) {
		this.downloadLinkLinux = downloadLinkLinux;
	}

	public String getFaqLink() {
		return faqLink;
	}

	public void setFaqLink(String faqLink) {
		this.faqLink = faqLink;
	}

}
