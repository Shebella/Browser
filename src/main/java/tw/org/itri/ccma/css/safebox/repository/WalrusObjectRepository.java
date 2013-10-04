package tw.org.itri.ccma.css.safebox.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.itri.ccma.css.safebox.model.walrus.WalrusObject;

public interface WalrusObjectRepository extends JpaRepository<WalrusObject, Long> {

	public WalrusObject findByBucketNameAndObjectKey(String bucket_name, String obj_key);

	public List<WalrusObject> findByBucketNameAndObjectKeyIn(String bucket_name,
			Collection<String> obj_key_list);

}
