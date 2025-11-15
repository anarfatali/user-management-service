package az.company.usermanagementservice.specification;

import az.company.usermanagementservice.domain.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class UserSpecification {

    public static Specification<UserEntity> filter(UserFilter filter) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<>();

            if (filter.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));
            }

            if (filter.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), filter.getPhone()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
