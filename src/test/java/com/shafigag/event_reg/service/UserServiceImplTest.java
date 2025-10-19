package com.shafigag.event_reg.service;

import com.shafigag.event_reg.data.entity.User;
import com.shafigag.event_reg.data.repo.UserRepository;
import com.shafigag.event_reg.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUser() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Clementine Jane");
        user.setEmail("clementinejane@example.com");

    }

    @Test
    void testCreateUserSavesUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Clementine Jane");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserByIdReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("clementinejane@example.com");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByIdThrowsException() {
        when(userRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(13L)).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("User not found with id: 13");
        verify(userRepository, times(1)).findById(13L);
    }

    @Test
    void getAllUsersReturnsList() {
        List<User> mockList = Arrays.asList(user, new User());

        when(userRepository.findAll()).thenReturn(mockList);

        List<User> list = userService.getAllUsers();

        assertThat(list).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUserModifiesUser() {
        User updatedUser = new User();
        updatedUser.setName("Julia Irvine");
        updatedUser.setEmail("juliairvine@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updatedUser);

        assertThat(result.getName()).isEqualTo("Julia Irvine");
        assertThat(result.getEmail()).isEqualTo("juliairvine@example.com");

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserThrowsException() {
        when(userRepository.findById(13L)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setName("Julia Irvine");
        updatedUser.setEmail("juliairvine@example.com");

        assertThatThrownBy(() -> userService.updateUser(13L, updatedUser)).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("User not found with id: 13");

        verify(userRepository, times(1)).findById(13L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserThrowsException() {
        when(userRepository.findById(13L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUser(13L)).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("User not found with id: 13");

        verify(userRepository, times(1)).findById(13L);
        verify(userRepository, never()).delete(any());
    }

}
