package com.github.renatocardosoalves.decoder.services.impl;

import com.github.renatocardosoalves.decoder.models.UserModel;
import com.github.renatocardosoalves.decoder.repositories.UserRepository;
import com.github.renatocardosoalves.decoder.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return this.userRepository.findById(userId);
    }

}
