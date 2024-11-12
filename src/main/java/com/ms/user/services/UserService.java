package com.ms.user.services;

import com.ms.user.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public UserModel save(UserModel userModel);

    public List<UserModel> getAll();

    public Optional<UserModel> findById(UUID id);

    public void delete(UUID id);
}
