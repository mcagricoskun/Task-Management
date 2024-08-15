package com.btk.bsd.mapper;

import com.btk.bsd.dto.UserDTO;
import com.btk.bsd.model.Role;
import com.btk.bsd.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testUserEntityToUserDTO() {
        // Örnek veri oluşturma
        Role role1 = new Role(1L, "ROLE_USER", null);
        Role role2 = new Role(2L, "ROLE_ADMIN", null);
        List<Role> roles = Arrays.asList(role1, role2);

        User user = new User(1L, "John Doe", roles, "john.doe@example.com");

        // Entity'den DTO'ya dönüştürme
        UserDTO userDTO = userMapper.userEntityToUserDTO(user);

        // Dönüşümü doğrulama
        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getRoles().size(), userDTO.getRoleIds().size());
        assertTrue(userDTO.getRoleIds().contains(role1.getId()));
        assertTrue(userDTO.getRoleIds().contains(role2.getId()));
    }

    @Test
    void testUserDTOtoUserEntity() {
        // Örnek veri oluşturma
        List<Long> roleIds = Arrays.asList(1L, 2L);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setRoleIds(roleIds);

        // DTO'dan Entity'ye dönüştürme
        User user = userMapper.userDTOToUserEntity(userDTO);

        // Dönüşümü doğrulama
        assertNotNull(user);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getRoleIds().size(), user.getRoles().size());
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getId().equals(1L)));
        assertTrue(user.getRoles().stream().anyMatch(role -> role.getId().equals(2L)));
    }
}
