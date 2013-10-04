package tw.org.itri.ccma.css.safebox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.org.itri.ccma.css.safebox.model.walrus.AuthUser;

public interface WalrusAuthUserRepository extends JpaRepository<AuthUser, Long> {

	public AuthUser findByAccessKeyAndSecretKey(String access_key, String secret_key);

}
