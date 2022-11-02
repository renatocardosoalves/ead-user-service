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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should return status 200 with a user list when calling findAll")
    void shouldReturnStatus200WithAUserListWhenCallingFindAll() throws Exception {
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
    @DisplayName("Should return status 200 with a user when calling findById with an existing id")
    void shouldReturnStatus200WithAUserWhenCallingFindByIdWithAnExistingId() throws Exception {
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
    @DisplayName("Should return status 404 when calling findById with a non-existent id")
    void shouldReturnStatus404WhenCallingFindByIdWithANonExistentId() throws Exception {
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

    @Test
    @DisplayName("Should return status 204 when calling deleteById with an existing id")
    void shouldReturnStatus204WhenCallingDeleteByIdWithANExistingId() throws Exception {
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

        doNothing()
                .when(this.userService)
                .delete(user);

        this.mockMvc.perform(delete("/users/{userId}", userId))
                .andExpectAll(
                        status().isNoContent()
                );

        verify(this.userService, times(1))
                .findById(userId);

        verify(this.userService, times(1))
                .delete(user);
    }

    @Test
    @DisplayName("Should return status 404 when calling deleteById with a non-existent id")
    void shouldReturnStatus404WhenCallingDeleteByIdWithANonExistentId() throws Exception {
        var userId = UUID.randomUUID();

        when(this.userService.findById(userId))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(delete("/users/{userId}", userId))
                .andExpectAll(
                        status().isNotFound()
                );

        verify(this.userService, times(1))
                .findById(userId);
    }

}