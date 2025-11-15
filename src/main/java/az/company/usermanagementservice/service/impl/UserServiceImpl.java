package az.company.usermanagementservice.service.impl;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.domain.entity.UserEntity;
import az.company.usermanagementservice.exception.AlreadyExistsException;
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

    @Transactional
    @Override
    public void createUser(CreateUserRequest request) {
        log.info("ActionLog.createUser.start - request={} ", request);
        var user = userMapper.toEntity(request);

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new AlreadyExistsException("Phone already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        userRepository.save(user);
        log.info("ActionLog.createUser.end - userId={}", user.getId());
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        //TODO: specification
        log.info("ActionLog.getAllUsers.start");
        var users = userRepository.findAll(pageable);
        log.info("ActionLog.getAllUsers.end - totalUsers: {}", users.getTotalElements());
        return users.map(userMapper::toResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("ActionLog.getUserById.start - userId={}", id);
        var user = findUserById(id);
        log.info("ActionLog.getUserById.end - user={}", user);
        return userMapper.toResponse(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("ActionLog.deleteUser.start - userId: {}", id);
        checkIfUserExists(id);
        userRepository.deleteById(id);
        log.info("ActionLog.deleteUser.end - userId: {}", id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, UpdateUserRequest request) {
        log.info("ActionLog.updateUser.start - request={}", request);
        var user = findUserById(id);

        userMapper.update(user, request);

        userRepository.save(user);
        log.info("ActionLog.updateUser.end - userId={}", id);
    }

    private UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        {
                            log.error("ActionLog.findUserById.error - userId={}", id);
                            return new NotFoundException("User not found with id: " + id);
                        }
                );
    }

    private void checkIfUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
    }
}
