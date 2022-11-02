package com.github.renatocardosoalves.decoder.services.impl;

import com.github.renatocardosoalves.decoder.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.renatocardosoalves.decoder.databuilders.UserModelBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should return a list of users when calling findAll")
    void shouldReturnAListOfUsersWhenCallingFindAll() {
        var users = List.of(
                aUser()
                        .withUserId(UUID.randomUUID())
                        .withUsername("fulano")
                        .withEmail("fulano@mail.com")
                        .withPassword("fulano123")
                        .withFullName("Fulano da Silva")
                        .withPhoneNumber("(11)1111-1111")
                        .withCpf("111.111.111-11")
                        .build(),
                aUser()
                        .withUserId(UUID.randomUUID())
                        .withUsername("beltrano")
                        .withEmail("beltrano@mail.com")
                        .withPassword("beltrano123")
                        .withFullName("Beltrano Oliveira")
                        .withPhoneNumber("(11)2222-2222")
                        .withCpf("222.222.222-22")
                        .build()
        );

        when(this.userRepository.findAll())
                .thenReturn(users);

        var expectedUsers = this.userService.findAll();

        assertThat(expectedUsers)
                .hasSize(2);
        assertThat(expectedUsers)
                .containsAll(users);
    }

    @Test
    @DisplayName("Should return a user optional when calling findById with an existing id")
    void shouldReturnAUserOptionalWhenCallingFindByIdWithAnExistingId() {
        var userId = UUID.randomUUID();

        var user = aUser()
                .withUserId(userId)
                .withUsername("fulano")
                .withEmail("fulano@mail.com")
                .withPassword("fulano123")
                .withFullName("Fulano da Silva")
                .withPhoneNumber("(11)1111-1111")
                .withCpf("111.111.111-11")
                .build();

        when(this.userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        var expectedUser = this.userService.findById(userId);

        verify(this.userRepository, times(1))
                .findById(userId);

        assertThat(expectedUser)
                .isNotEmpty();
    }

    @Test
    @DisplayName("Should return an empty user optional when calling findById with a non-existent id")
    void shouldReturnAnEmptyUserOptionalWhenCallingFindByIdWithANonExistentId() {
        var userId = UUID.randomUUID();

        when(this.userRepository.findById(userId))
                .thenReturn(Optional.empty());

        var expectedUser = this.userService.findById(userId);

        verify(this.userRepository, times(1))
                .findById(userId);

        assertThat(expectedUser)
                .isEmpty();
    }

    @Test
    @DisplayName("Should delete a user when calling delete")
    void shouldDeleteAUserWhenCallingDelete() {
        var user = aUser()
                .withUserId(UUID.randomUUID())
                .withUsername("fulano")
                .withEmail("fulano@mail.com")
                .withPassword("fulano123")
                .withFullName("Fulano da Silva")
                .withPhoneNumber("(11)1111-1111")
                .withCpf("111.111.111-11")
                .build();

        doNothing()
                .when(this.userRepository)
                .delete(user);

        this.userService.delete(user);

        verify(this.userRepository, times(1))
                .delete(user);
    }

}