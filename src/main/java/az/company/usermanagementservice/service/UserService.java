package az.company.usermanagementservice.service;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.DeleteUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    void createUser(CreateUserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void updateUser(UpdateUserRequest request);

    void deleteUser(DeleteUserRequest request);
}
