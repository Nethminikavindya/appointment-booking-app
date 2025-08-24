package repository;

import com.nethmini.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

//this repo works for user table, unique identifier of that class
public interface UserRepository  extends JpaRepository<User, Long> {
}
