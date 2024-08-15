package com.btk.bsd.service;


import com.btk.bsd.dto.UserDTO;
import com.btk.bsd.mapper.UserMapper;
import com.btk.bsd.model.Role;
import com.btk.bsd.model.User;
import com.btk.bsd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    // TODO logger yap


    // @Autowired yerine böyle yaptım constr oluşturmak gerekli autowired kullnmazsan
    // final kullanma nedeni userRepository başka nesneye atanmayacağının garanti edilmesi
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private UserMapper userMapper;

    //GET

    // UserRepo User alır findById ile User bulunup cevap olarak DTO dönülecek
    // Cacheable veriyi ön belleğe alır
    // CacheEvicts veri silinince önbelleği de siler
    // CachePut veri güncellenince önbelleği bu veri ile günceller
    @Cacheable(value = "user", key = "#id")
    public UserDTO getUserById (Long id){
        return userRepository.findById(id)
                .map(userMapper::userToUserDTO)
                .orElse(null);
    }

    // GET ALL

    // Herbir liste eleman üzerinde işlem olduğu için burada stream olacak
    // Yukarıda optional kullanılıyor tek bir nesne üzerinde çalışılıp çalışılmadığımnını kontrolü içiin
    @Cacheable(value = "users")
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    //CREATE

    @CachePut(value = "user", key = "#result.id")
    public UserDTO createUser(UserDTO userDTO){
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    // UPDATE

    @CachePut(value = "user", key = "#id")
    public UserDTO updateUser(Long id, UserDTO userDTO){

        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setRoles(userDTO.getRoleIds().stream()
                    .map(roleId -> new Role(roleId, null, null))
                    .collect(Collectors.toList()));
            user = userRepository.save(user);
            return userMapper.userToUserDTO(user);
        }
        return null;
    }
    // DELETE

    // id ile gelirse hem onu cache den silsin hemde allentries ile tümünü silsinki getalluser metodu çağrılırsa cache den gelmesin
    @CacheEvict(value = {"user", "users"}, key = "#id", allEntries = true)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
