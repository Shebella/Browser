package tw.org.itri.ccma.css.safebox.service.client;

import java.util.List;

import tw.org.itri.ccma.css.safebox.model.client.BucketInfoResult;
import tw.org.itri.ccma.css.safebox.model.client.RenameObjectResult;
import tw.org.itri.ccma.css.safebox.model.client.SyncRequestResult;
import tw.org.itri.ccma.css.safebox.model.client.UnlockResult;
import tw.org.itri.ccma.css.safebox.model.walrus.WalrusObject;

public interface IEventSyncService {

	public static enum UNLOCK_TYPE {
		SUCC("SUCC"), FAIL("FAIL"), PENDING_FOR_MERGE("PENDING");

		private String text;

		UNLOCK_TYPE(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}
	}

	public abstract List<WalrusObject> getWalrusObjectList(String bucket_name, List<String> object_key_list);

	public abstract String getUUIDByObject(String bucket_name, String object_key);

	public abstract SyncRequestResult performSyncRequest(String member_account, String instance_key);

	public abstract UnlockResult performUnlockSync(String member_account, String instance_key, String unlock_type);

	public abstract RenameObjectResult performRenameObject(String member_account, String bucket_name,
			String src_name, String dest_name);

	public abstract BucketInfoResult performBucketInfoQuery(String member_account, String bucket_name);

}
