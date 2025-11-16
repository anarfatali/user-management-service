package az.company.usermanagementservice.service;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.domain.entity.UserEntity;
import az.company.usermanagementservice.exception.AlreadyExistsException;
import az.company.usermanagementservice.exception.NotFoundException;
import az.company.usermanagementservice.kafka.producer.UserProducer;
import az.company.usermanagementservice.mapper.UserMapper;
import az.company.usermanagementservice.repository.UserRepository;
import az.company.usermanagementservice.service.impl.UserServiceImpl;
import az.company.usermanagementservice.specification.UserFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        CreateUserRequest request = new CreateUserRequest();
        UserEntity user = new UserEntity();
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        userService.createUser(request);

        verify(userRepository, times(1)).save(user);
        verify(userProducer, times(1)).sendUserCreated(any());
    }

    @Test
    void createUser_alreadyExists_throwsException() {
        CreateUserRequest request = new CreateUserRequest();

        UserEntity user = new UserEntity();

        request.setEmail("a@gmail.com");
        request.setPhone("123456789");

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        when(userRepository.existsByPhone(anyString())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(request));
    }

    @Test
    void getAllUsers_returnsPage() {
        UserFilter filter = new UserFilter();
        Pageable pageable = Pageable.unpaged();
        Page<UserEntity> page = new PageImpl<>(List.of(new UserEntity()));
        when(userRepository.findAll((Specification<UserEntity>) any(), eq(pageable)))
                .thenReturn(page);
        when(userMapper.toResponse(any())).thenReturn(new UserResponse());

        Page<UserResponse> result = userService.getAllUsers(filter, pageable);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getUserById_found() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(new UserResponse());

        UserResponse response = userService.getUserById(1L);
        assertNotNull(response);
    }

    @Test
    void getUserById_notFound_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void deleteUser_success() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
        verify(userProducer, times(1)).sendUserDeleted(any());
    }

    @Test
    void updateUser_success() {
        UserEntity user = new UserEntity();
        UpdateUserRequest request = new UpdateUserRequest();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateUser(1L, request);

        verify(userRepository, times(1)).save(user);
        verify(userProducer, times(1)).sendUserUpdated(any());
    }
}
