package com.hillel.online_shop.service;

import com.hillel.online_shop.dto.UserDTO;
import com.hillel.online_shop.dto.UserInfoDTO;
import com.hillel.online_shop.exception.UserNotFoundException;
import com.hillel.online_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private void save(UserDTO userDetails) {
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        userRepository.save(modelMapper.map(userDetails, com.hillel.online_shop.entity.User.class));
    }

    public UserInfoDTO getAccountInfo(long id) {
        return mapUserInfo(findById(id));
    }

    @Override
    public UserDTO getById(long id) {
        return mapUser(findById(id));
    }

    @Override
    public List<UserDTO> getAll() {
        return null;
    }

    @Override
    public Long create(UserDTO userDTO) {
        return null;
    }

    @Override
    public Long update(UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    private UserDTO mapUser(com.hillel.online_shop.entity.User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private com.hillel.online_shop.entity.User mapUser(UserDTO productDTO) {
        return modelMapper.map(productDTO, com.hillel.online_shop.entity.User.class);
    }

    private com.hillel.online_shop.entity.User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found!"));
    }

    private UserInfoDTO mapUserInfo(com.hillel.online_shop.entity.User user) {
        return modelMapper.map(user, UserInfoDTO.class);
    }
}
