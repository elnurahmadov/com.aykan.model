package com.aykan.service.user.impl;

import com.aykan.dao.user.RoleRepository;
import com.aykan.dao.user.UserRepository;
import com.aykan.domain.user.Role;
import com.aykan.domain.user.User;
import com.aykan.exception.UserAlreadyExistException;
import com.aykan.service.user.UserService;
import com.aykan.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(UserDto userDto) {
        boolean status = usernameExist(userDto.getUsername());

        if (status) {
            throw new UserAlreadyExistException("There is an acount with that username: " + userDto.getUsername());
        }
        Role role = roleRepository.findRoleByName("ROLE_USER");
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        return userRepository.saveUser(user);
    }

    private boolean usernameExist(String username) {
        return findUserByUsername(username) != null;
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.updateUser(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public User deleteUser(User user) {
        return userRepository.deleteUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
