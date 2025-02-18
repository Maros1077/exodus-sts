package cz.exodus.sts.db.repository;

import cz.exodus.sts.db.entity.ClientConfigEntity;
import cz.exodus.sts.db.entity.ClientEntity;
import cz.exodus.sts.db.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByJti(String jti);
}
