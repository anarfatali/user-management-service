package az.company.usermanagementservice.repository;

import az.company.usermanagementservice.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
