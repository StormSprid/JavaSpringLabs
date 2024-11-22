package stormsprid.emilspring.security;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stormsprid.emilspring.Entity.User;
import stormsprid.emilspring.Repository.UserRepository;


@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    UserRepository userRepository;
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown login!"));
        return UserDetailsImpl.build(user);
    }
}