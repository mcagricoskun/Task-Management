package com.btk.bsd.mapper;

import com.btk.bsd.dto.UserDTO;
import com.btk.bsd.model.Role;
import com.btk.bsd.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper(); // ModelMapper'ı manuel olarak oluşturuyoruz
    }

    public UserDTO userToUserDTO(User user) {
        // User entity'sini UserDTO'ya dönüştürüyoruz
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // roleIds alanını dolduruyoruz
        userDTO.setRoleIds(user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList()));

        return userDTO;
    }

    public User userDTOToUser(UserDTO userDTO) {
        // UserDTO'yu User entity'sine dönüştürüyoruz
        User user = modelMapper.map(userDTO, User.class);

        // roles alanını dolduruyoruz
        user.setRoles(userDTO.getRoleIds().stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                })
                .collect(Collectors.toList()));

        return user;
    }
}
