package uz.pdp.warehouseapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouseapp.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
     Optional<User> findByEmail(String email);
     Optional<User>findAllByEmailOrPhoneNumber(String email,String phoneNumber);
    void deleteByEmail(String email);
}

