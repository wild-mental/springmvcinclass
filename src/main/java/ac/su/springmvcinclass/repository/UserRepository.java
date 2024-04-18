package ac.su.springmvcinclass.repository;

import ac.su.springmvcinclass.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // DAO (Data Access Object) 를 다루며 영속성을 관리 : DB 접근
public interface UserRepository extends JpaRepository<User, Long> {
}
