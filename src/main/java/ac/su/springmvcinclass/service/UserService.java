package ac.su.springmvcinclass.service;

import ac.su.springmvcinclass.domain.User;
import ac.su.springmvcinclass.exception.UserNotFoundException;
import ac.su.springmvcinclass.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        // 구체적인 조건, 처리 세부사항 등의 로직을
        // Service Layer 에서 처리한다.
        return userRepository.findAll();
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        // 1. throw case 에러 catch
        // 2. dummy 객체 리턴
        // 3. custom error 페이지로 이동
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser); // id 값이 새로 생성 되므로, SQL create 문 수행
    }

    public User updateUser(Long id, User updatedUser) {
        // 유저 검색
        User registeredUser = getUserByID(id);
        registeredUser.setName(updatedUser.getName());
        registeredUser.setEmail(updatedUser.getEmail());
        return userRepository.save(registeredUser); // id 값이 이미 존재 하므로 SQL update 문 수행
    }

    public User patchUser(Long id, User updatedUser) {
        // PATCH 의 경우 user 데이터 일부 필드만 수신될 가능성 고려해서 Validation
        // 모든 필드 비어있을 가능성도 있음
        // 없는 유저 대상 exception 처리도 여전히 상존
        User existingUser = getUserByID(id);
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
