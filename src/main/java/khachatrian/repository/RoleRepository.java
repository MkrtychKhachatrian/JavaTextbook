package khachatrian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import khachatrian.model.RoleUser;

@Repository
public interface RoleRepository extends JpaRepository<RoleUser, Long> {

}
