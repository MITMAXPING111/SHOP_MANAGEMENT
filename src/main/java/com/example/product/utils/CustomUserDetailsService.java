package com.example.product.utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.product.entities.users.Customer;
import com.example.product.entities.users.User;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepo;

        @Autowired
        private CustomerRepository customerRepo;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<User> userOpt = userRepo.findByEmail(email);
                if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        Set<GrantedAuthority> authorities = new HashSet<>();
                        authorities.addAll(user.getRoles().stream()
                                        .flatMap(role -> role.getPermissions().stream())
                                        .map(perm -> new SimpleGrantedAuthority(perm.getName().toString()))
                                        .toList());

                        return new org.springframework.security.core.userdetails.User(
                                        user.getEmail(),
                                        user.getPassword(),
                                        authorities);
                }

                Optional<Customer> customerOpt = customerRepo.findByEmail(email);
                if (customerOpt.isPresent()) {
                        Customer customer = customerOpt.get();
                        Set<GrantedAuthority> authorities = new HashSet<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

                        return new org.springframework.security.core.userdetails.User(
                                        customer.getEmail(),
                                        customer.getPassword(),
                                        authorities);
                }

                throw new UsernameNotFoundException("Email not found in User or Customer");
        }
}
