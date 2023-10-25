package academy.devdojo.springbootessentialsrr.service;

import academy.devdojo.springbootessentialsrr.repository.DevRodUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevRodUserDetailsService implements UserDetailsService {
    private final DevRodUserRepository devRodUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(devRodUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("DevRod user not found"));
    }
}
