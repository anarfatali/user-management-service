package az.company.usermanagementservice.repository;

import az.company.usermanagementservice.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);
}
