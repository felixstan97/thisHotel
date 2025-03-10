package com.thishotel.repository;

import com.thishotel.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
//    @Query("select * from ")
    PasswordResetToken findByToken(String token);
}
