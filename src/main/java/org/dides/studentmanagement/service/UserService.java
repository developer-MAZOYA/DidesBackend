package org.dides.studentmanagement.service;

import org.dides.studentmanagement.dto.AuthRequest;
import org.dides.studentmanagement.dto.UserDTO;
import org.dides.studentmanagement.model.Users;
import org.dides.studentmanagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    // REGISTER USER (no role, no enabled flag)
    public UserDTO register(UserDTO userDTO) {
        Users user = new Users();
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));


        Users savedUser = userRepo.save(user);

        return new UserDTO(
                savedUser.getFirstname(),
                savedUser.getLastname(),
                savedUser.getEmail(),
                savedUser.getPassword()

        );
    }

    // VERIFY USER LOGIN
    public String verify(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Users user = userRepo.findByEmail(authRequest.getEmail());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            return jwtService.generateToken(
                    user.getEmail(),
                    user.getFirstname(),
                    user.getLastname()

            );
        } else {
            return "fail";
        }
    }

    // CHANGE PASSWORD
    public ResponseEntity<?> changePassword(String authorizationHeader,
                                            String currentPassword,
                                            String newPassword) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Missing or invalid Authorization header"));
            }

            if (!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid or expired token"));
            }

            String email = jwtService.extractUsername(token);
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid token payload"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, currentPassword)
            );

            if (authentication.isAuthenticated()) {
                Users user = userRepo.findByEmail(email);
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Collections.singletonMap("error", "User not found"));
                }


                user.setPassword(encoder.encode(newPassword));
                userRepo.save(user);

                return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully"));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Current password is incorrect"));
        } catch (Exception e) {
            System.err.println("Password change error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Password change failed. Please try again."));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Authentication failed"));
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    // LIST USERS
    public List<UserDTO> list() {
        return userRepo.findAll()
                .stream()
                .map(user -> new UserDTO(

                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword()

                ))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepo.findById(id)
                .map(user -> new UserDTO(
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword()
                ));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }

        Users updatedUser = userRepo.save(user);

        return new UserDTO(
                updatedUser.getFirstname(),
                updatedUser.getLastname(),
                updatedUser.getEmail(),
                updatedUser.getPassword()

        );
    }

    public Users getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<String> getAllNames() {
        return userRepo.findAll()
                .stream()
                .map(Users::getFirstname)
                .toList();
    }
}
