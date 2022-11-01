package com.github.renatocardosoalves.decoder.databuilders;

import com.github.renatocardosoalves.decoder.enums.UserStatus;
import com.github.renatocardosoalves.decoder.enums.UserType;
import com.github.renatocardosoalves.decoder.models.UserModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserModelBuilder {

    private final UserModel userModel;

    private UserModelBuilder() {
        this.userModel = new UserModel();
        this.userModel.setUserStatus(UserStatus.ACTIVE);
        this.userModel.setUserType(UserType.STUDENT);
        this.userModel.setImageUrl("http://image.com");
        this.userModel.setCreationDate(LocalDateTime.now());
        this.userModel.setLastUpdateDate(LocalDateTime.now());
    }

    public static UserModelBuilder aUser() {
        return new UserModelBuilder();
    }

    public UserModelBuilder withUserId(UUID userId) {
        this.userModel.setUserId(userId);
        return this;
    }

    public UserModelBuilder withUsername(String username) {
        this.userModel.setUsername(username);
        return this;
    }

    public UserModelBuilder withEmail(String email) {
        this.userModel.setEmail(email);
        return this;
    }

    public UserModelBuilder withPassword(String password) {
        this.userModel.setPassword(password);
        return this;
    }

    public UserModelBuilder withFullName(String fullName) {
        this.userModel.setFullName(fullName);
        return this;
    }

    public UserModelBuilder withPhoneNumber(String phoneNumber) {
        this.userModel.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserModelBuilder withCpf(String cpf) {
        this.userModel.setCpf(cpf);
        return this;
    }

    public UserModel build() {
        return this.userModel;
    }

}
