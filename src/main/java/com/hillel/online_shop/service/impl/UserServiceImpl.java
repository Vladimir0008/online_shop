package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.user.UserDTO;
import com.hillel.online_shop.dto.user.UserInfoDTO;
import com.hillel.online_shop.exception.UserNotFoundException;
import com.hillel.online_shop.repository.UserRepository;
import com.hillel.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byLogin = userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("invalid login"));

        return User.builder()
                .username(byLogin.getLogin())
                .password(byLogin.getPassword())
                .authorities(byLogin.getRole().name())
                .build();
    }

    @Override
    public Long create(UserDTO userDTO) {
        if (userDTO.getId() != null) {
                throw new IllegalArgumentException("field \"id\" must be null");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(map(userDTO)).getId();
    }

    @Override
    public void update(UserDTO userDTO) {
        if(userDTO.getId() == null) {
            throw new IllegalArgumentException("field \"id\" must not be null");
        }
        findById(userDTO.getId());
        userRepository.save(map(userDTO));
    }

    @Override
    public void delete(long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getById(long id) {
        return map(findById(id));
    }

    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::map)
                .collect(Collectors.toList());
    }

    public UserInfoDTO getInfoById(long id) {
        return mapInfo(findById(id));
    }

    public List<UserInfoDTO> getAllInfo() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::mapInfo)
                .collect(Collectors.toList());
    }

    private com.hillel.online_shop.entity.User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id " + id + " not found!"));
    }

    private UserDTO map(com.hillel.online_shop.entity.User user) {
        return map(user, UserDTO.class);
    }

    private com.hillel.online_shop.entity.User map(UserDTO userDTO) {
        return map(userDTO, com.hillel.online_shop.entity.User.class);
    }

    private UserInfoDTO mapInfo(com.hillel.online_shop.entity.User user) {
        return map(user, UserInfoDTO.class);
    }

    private <T, U> T map(U source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}