package com.github.renatocardosoalves.decoder.repositories;

import com.github.renatocardosoalves.decoder.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
