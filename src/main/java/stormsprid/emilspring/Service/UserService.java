package stormsprid.emilspring.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stormsprid.emilspring.Entity.User;
import stormsprid.emilspring.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    private BCryptPasswordEncoder encoder(){return new BCryptPasswordEncoder();}
    public void SaveUser(User user){
        user.setPassword(encoder().encode(user.getPassword()));
        System.out.println( user);
        userRepository.save(user);
    }
    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
