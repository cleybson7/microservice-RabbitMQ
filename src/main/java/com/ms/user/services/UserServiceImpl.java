package com.ms.user.services;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserServiceImpl(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    @Override
    public UserModel save(UserModel userModel) {
        userModel =  userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);

        return userModel;
    }

    @Transactional
    @Override
    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    @Transactional(rollbackFor = {NoSuchElementException.class})
    @Override
    public Optional<UserModel> findById(UUID id) {
        if (!userRepository.existsById(id)){
            throw new NoSuchElementException("Usuario com o ID: " + id + " não encontrado");
        }
        return userRepository.findById(id);
    }

    @Transactional(rollbackFor = {NoSuchElementException.class})
    @Override
    public void delete(UUID id) {
        if (!userRepository.existsById(id)){
            throw new NoSuchElementException("Usuario com o ID: " + id + " não encontrado");
        }
        userRepository.deleteById(id);
    }
}
