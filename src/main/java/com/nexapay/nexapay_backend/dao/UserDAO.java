package com.nexapay.nexapay_backend.dao;

import com.nexapay.nexapay_backend.dto.request.UserRequest;
import com.nexapay.nexapay_backend.dto.request.UserResponse;
import com.nexapay.nexapay_backend.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class UserDAO {
    @Autowired
    UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity(new Random().nextInt(1000), userRequest.getFullName(),
                userRequest.getEmailAddress(), userRequest.getPassword());
        userRepository.save(userEntity);
        return new UserResponse(userEntity.getFullName(), userRequest.getEmailAddress(), userEntity.getPassword());
    }

    public UserResponse readById(Integer id) {
        UserEntity userEntity = userRepository.getById(id);
        return new UserResponse(userEntity.getFullName(), userEntity.getEmail(), userEntity.getPassword());
    }

    public UserResponse readByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        return new UserResponse(userEntity.getFullName(), userEntity.getEmail(), userEntity.getPassword());
    }

    public List<UserEntity> readAll() {
        return userRepository.findAll();
    }

    public void updateById(Integer id, UserRequest reqDTO) {
        UserEntity userEntity = userRepository.getById(id);
        if(reqDTO.getFullName()!=null) {
            userEntity.setFullName(reqDTO.getFullName());
        }
        if(reqDTO.getEmailAddress()!=null) {
            userEntity.setEmail(reqDTO.getEmailAddress());
        }
        userRepository.save(userEntity);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
