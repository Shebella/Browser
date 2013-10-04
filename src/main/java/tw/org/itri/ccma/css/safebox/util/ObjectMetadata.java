package tw.org.itri.ccma.css.safebox.util;

import org.jets3t.service.Constants;

public enum ObjectMetadata {
	MTIME("mtime"), ACTION("action"), CLIENT_TYPE("clientType"), INSTANCE_ID("instId");

	private String text;

	ObjectMetadata(String text) {
		this.text = text;
	}

	public String toString() {
		return Constants.REST_METADATA_PREFIX + this.text;
	}

}
