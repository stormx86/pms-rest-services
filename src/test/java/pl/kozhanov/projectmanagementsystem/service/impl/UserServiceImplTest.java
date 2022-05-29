package pl.kozhanov.projectmanagementsystem.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.kozhanov.projectmanagementsystem.domain.User;
import pl.kozhanov.projectmanagementsystem.dto.UserDto;
import pl.kozhanov.projectmanagementsystem.repos.UserRepo;
import pl.kozhanov.projectmanagementsystem.service.mapping.OrikaBeanMapper;

import java.util.Collections;

import static java.util.Optional.of;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.*;
import static pl.kozhanov.projectmanagementsystem.domain.GlobalRole.ROLE_ADMIN;
import static pl.kozhanov.projectmanagementsystem.domain.GlobalRole.ROLE_USER;

public class UserServiceImplTest {

    private static final Integer USER_ID = 100;

    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private OrikaBeanMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        reset(userRepo, passwordEncoder, mapper);
    }

    @Test
    public void testFindAll() {
        //given
        final User user = new User();
        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));

        //when
        userService.findAll();

        //then
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void shouldGetAllUsers() {
        //given
        final User user = new User();
        final UserDto userDto = new UserDto();
        when(userRepo.findAllByOrderByUsernameAsc()).thenReturn(Collections.singletonList(user));
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        //when
        userService.getAllUsers();

        //then
        verify(userRepo, times(1)).findAllByOrderByUsernameAsc();
        verify(mapper, times(1)).map(any(User.class), eq(UserDto.class));
    }

    @Test
    public void shouldUpdateUserRoles() {
        //given
        final User user = new User();
        final UserDto userDto = new UserDto();
        userDto.setGlobalRoles(Collections.singleton(ROLE_ADMIN));
        when(userRepo.findById(USER_ID)).thenReturn(of(user));
        when(userRepo.findAllByOrderByUsernameAsc()).thenReturn(Collections.singletonList(user));
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        //when
        userService.updateUserRoles(USER_ID, userDto);

        //then
        assertTrue(user.getGlobalRoles().contains(ROLE_ADMIN));
        verify(userRepo, times(1)).findById(anyInt());
        verify(userRepo, times(1)).findAllByOrderByUsernameAsc();
        verify(mapper, times(1)).map(any(User.class), eq(UserDto.class));

    }

    @Test
    public void shouldAddNewUser() {
        //given
        final UserDto userDto = new UserDto();
        final User user = new User();
        when(mapper.map(any(UserDto.class), eq(User.class))).thenReturn(user);
        when(userRepo.findAllByOrderByUsernameAsc()).thenReturn(Collections.singletonList(user));
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        //when
        userService.addNewUser(userDto);

        //then
        verify(mapper, times(1)).map(any(UserDto.class), eq(User.class));
        verify(userRepo, times(1)).findAllByOrderByUsernameAsc();
        verify(mapper, times(1)).map(any(User.class), eq(UserDto.class));
        verify(userRepo, times(1)).save(any());
    }

    @Test
    public void shouldDeleteUser() {
        //given
        final User user = new User();
        final UserDto userDto = new UserDto();
        when(userRepo.findById(USER_ID)).thenReturn(of(user));
        when(userRepo.findAllByOrderByUsernameAsc()).thenReturn(Collections.singletonList(user));
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);

        //when
        userService.deleteUser(USER_ID);

        //then
        verify(userRepo, times(1)).findById(anyInt());
        verify(userRepo, times(1)).findAllByOrderByUsernameAsc();
        verify(mapper, times(1)).map(any(User.class), eq(UserDto.class));
        verify(userRepo, times(1)).delete(any());
    }

    @Test
    public void shouldResetUserPassword() {
        //given
        final User user = new User();
        user.setUsername("Username");
        final String newPassword = "NewPassword";
        when(userRepo.findById(USER_ID)).thenReturn(of(user));
        when(passwordEncoder.encode(anyString())).thenReturn(newPassword);

        //when
        userService.resetUserPassword(USER_ID);

        //then
        assertNotNull(user.getPassword());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepo, times(1)).findById(anyInt());
    }

    @Test
    public void shouldChangeUserPassword() {
        //given
        final User user = new User();
        final String newPassword = "NewPassword";
        final UserDto userDto = new UserDto();
        userDto.setUserId(USER_ID);
        userDto.setPassword("password");
        when(userRepo.findById(USER_ID)).thenReturn(of(user));
        when(passwordEncoder.encode(anyString())).thenReturn(newPassword);
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);
        
        //when
        userService.changeUserPassword(userDto);

        //then
        assertEquals(user.getPassword(), "NewPassword");
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepo, times(1)).findById(anyInt());
        verify(mapper, times(1)).map(any(User.class), eq(UserDto.class));
    }

    @Test
    public void shouldReturnTrueIfUserIsAdmin() {
        //given
        final User user = new User();
        user.setGlobalRoles(Collections.singleton(ROLE_ADMIN));

        //when
        final boolean result = userService.isAdmin(user);

        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUserIsNotAdmin() {
        //given
        final User user = new User();
        user.setGlobalRoles(Collections.singleton(ROLE_USER));

        //when
        final boolean result = userService.isAdmin(user);

        //then
        assertFalse(result);
    }
}