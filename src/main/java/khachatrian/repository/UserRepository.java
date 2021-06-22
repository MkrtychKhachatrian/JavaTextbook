package khachatrian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import khachatrian.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
