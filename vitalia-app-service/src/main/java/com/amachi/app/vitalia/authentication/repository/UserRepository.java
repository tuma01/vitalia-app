package com.amachi.app.vitalia.authentication.repository;

import com.amachi.app.vitalia.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);



//    default Page<User> getUsers(UserSearchDto userSearchDto, Pageable pageable) {
//        return findAll(new UserSpecification(userSearchDto), pageable);
//    }

//    Page<User> findByUsernameLike(String username, Pageable pageable);
}
