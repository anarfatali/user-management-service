package az.company.usermanagementservice.controller;

import az.company.usermanagementservice.domain.dto.request.CreateUserRequest;
import az.company.usermanagementservice.domain.dto.request.UpdateUserRequest;
import az.company.usermanagementservice.domain.dto.response.UserResponse;
import az.company.usermanagementservice.service.UserService;
import az.company.usermanagementservice.specification.UserFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_callsService() {
        CreateUserRequest request = new CreateUserRequest();
        userController.createUser(request);
        verify(userService, times(1)).createUser(request);
    }

    @Test
    void getAllUsers_returnsPage() {
        Page<UserResponse> page = new PageImpl<>(List.of(new UserResponse()));
        when(userService.getAllUsers(any(UserFilter.class), any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<UserResponse>> response = userController.getAllUsers(new UserFilter(), Pageable.unpaged());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void getUserById_returnsUser() {
        UserResponse user = new UserResponse();
        when(userService.getUserById(1L)).thenReturn(user);

        ResponseEntity<UserResponse> response = userController.getUserById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void deleteUserById_callsService() {
        userController.deleteUserById(1L);
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void updateUser_callsService() {
        UpdateUserRequest request = new UpdateUserRequest();
        userController.updateUser(1L, request);
        verify(userService, times(1)).updateUser(1L, request);
    }
}
