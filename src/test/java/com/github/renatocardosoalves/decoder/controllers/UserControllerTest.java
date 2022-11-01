package com.github.renatocardosoalves.decoder.controllers;

import com.github.renatocardosoalves.decoder.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.renatocardosoalves.decoder.databuilders.UserModelBuilder.aUser;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should return a list of users when calling findAll")
    void shouldReturnAListOfUsersWhenCallingFindAll() throws Exception {
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

        when(this.userService.findAll())
                .thenReturn(users);

        this.mockMvc.perform(get("/users"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$..username", contains("fulano", "beltrano"))
                );

        verify(this.userService, times(1))
                .findAll();
    }

    @Test
    @DisplayName("Should return a user optional with existing id")
    void shouldReturnAUserOptionalWithExistingId() throws Exception {
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

        when(this.userService.findById(userId))
                .thenReturn(Optional.of(user));

        this.mockMvc.perform(get("/users/{userId}", userId))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(APPLICATION_JSON_VALUE),
                        jsonPath("$.userId", equalTo(userId.toString()))
                );

        verify(this.userService, times(1))
                .findById(userId);
    }

    @Test
    @DisplayName("Should return an ampty user optional with nonexistent id")
    void shouldReturnAnEmptyUserOptionalWithNonexistentId() throws Exception {
        var userId = UUID.randomUUID();

        when(this.userService.findById(userId))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(get("/users/{userId}", userId))
                .andExpectAll(
                        status().isNotFound()
                );

        verify(this.userService, times(1))
                .findById(userId);
    }

}