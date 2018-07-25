package pl.pkopy.login.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pkopy.login.models.entities.RegisterEntity;

import java.util.List;

@Repository
public interface RegisterRepository extends CrudRepository<RegisterEntity, Integer> {
    boolean existsByRegisterKey(String registerKey);
    boolean existsByIp(String ip);
    List<RegisterEntity> findAllByIp(String ip);
    RegisterEntity findByRegisterKey(String registryKey);
}
