package az.company.usermanagementservice.service.impl;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.DeleteUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.exception.NotFoundException;
import az.company.usermanagementservice.mapper.UserMapper;
import az.company.usermanagementservice.repository.UserRepository;
import az.company.usermanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("ActionLog.getAllUsers.start");
        var users = userRepository.findAll(pageable);
        log.info("ActionLog.getAllUsers.end - totalUsers: {}", users.getTotalElements());
        return users.map(userMapper::toResponse);
    }

    @Override
    public void updateUser(UpdateUserRequest request) {

    }

    @Override
    public void deleteUser(DeleteUserRequest request) {

    }

    @Transactional
    @Override
    public void createUser(CreateUserRequest request) {
        log.info("ActionLog.createUser.start - request={} ", request);
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        log.info("ActionLog.createUser.end - userId={}", user.getId());
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("ActionLog.getUserById.start - userId={}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() ->
                        {
                            log.error("ActionLog.getUserById.error - userId={}", id);
                            return new NotFoundException("User not found with id: " + id);
                        }
                );
        log.info("ActionLog.getUserById.end - user={}", user);
        return userMapper.toResponse(user);
    }
}
