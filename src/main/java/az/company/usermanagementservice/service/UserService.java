package az.company.usermanagementservice.service;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.specification.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getAllUsers(UserFilter filter, Pageable pageable);

    void updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}
