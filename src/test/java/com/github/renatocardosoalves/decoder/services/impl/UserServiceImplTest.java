package com.github.renatocardosoalves.decoder.services.impl;

import com.github.renatocardosoalves.decoder.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.github.renatocardosoalves.decoder.databuilders.UserModelBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
}