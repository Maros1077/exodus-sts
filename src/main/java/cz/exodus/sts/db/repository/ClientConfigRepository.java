package cz.exodus.sts.db.repository;

import cz.exodus.sts.db.entity.ClientConfigEntity;
import cz.exodus.sts.db.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientConfigRepository extends JpaRepository<ClientConfigEntity, Long> {

    ClientConfigEntity findByClientAndScopes(ClientEntity client, String scope);
}
