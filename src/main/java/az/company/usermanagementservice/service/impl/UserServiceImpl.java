package az.company.usermanagementservice.service.impl;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.DeleteUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Override
    public List<UserResponse> getAllUsers() {
        return null;
    }

    @Override
    public void updateUser(UpdateUserRequest request) {

    }

    @Override
    public void deleteUser(DeleteUserRequest request) {

    }

    @Override
    public void createUser(CreateUserRequest request) {

    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }
}
