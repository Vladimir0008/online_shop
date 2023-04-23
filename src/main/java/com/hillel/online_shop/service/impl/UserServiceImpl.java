package com.hillel.online_shop.service.impl;

import com.hillel.online_shop.dto.cart.CartRequestDTO;
import com.hillel.online_shop.dto.user.UserRequestDTO;
import com.hillel.online_shop.dto.user.UserResponseDTO;
import com.hillel.online_shop.entity.User;
import com.hillel.online_shop.exception.UserNotFoundException;
import com.hillel.online_shop.repository.UserRepository;
import com.hillel.online_shop.service.CartService;
import com.hillel.online_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
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
public class UserServiceImpl implements UserDetailsService, UserService<UserRequestDTO, UserResponseDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var byLogin = userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("invalid login"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(byLogin.getLogin())
                .password(byLogin.getPassword())
                .authorities(byLogin.getRole().name())
                .build();
    }

    @Override
    public UserResponseDTO findByLogin(String login) {
        var byLogin = userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("invalid login"));
        return modelMapper.map(byLogin, UserResponseDTO.class);
    }

    @Override
    public Long create(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getId() != null && userRepository.existsById(userRequestDTO.getId())) {
            throw new DuplicateKeyException("id " + userRequestDTO.getId() + " already exists");
        }
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User user = userRepository.save(modelMapper.map(userRequestDTO, User.class));
        createCart(user);

        return user.getId();
    }

    @Override
    public void update(UserRequestDTO userDTO) {
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO findById(long id) {
        return modelMapper.map(getById(id), UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    private User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user with id " + id + " not found!"));
    }

    private void createCart(User user) {
        CartRequestDTO cartDTO = new CartRequestDTO();
        cartDTO.setUser(modelMapper.map(user, UserRequestDTO.class));

        cartService.create(cartDTO);
    }
}