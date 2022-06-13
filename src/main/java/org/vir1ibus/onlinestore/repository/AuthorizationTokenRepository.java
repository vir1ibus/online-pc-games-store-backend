package org.vir1ibus.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vir1ibus.onlinestore.entity.AuthorizationToken;
import org.vir1ibus.onlinestore.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AuthorizationTokenRepository extends CrudRepository<AuthorizationToken, Integer> {
    AuthorizationToken findTokenByValue(@NotBlank String value);
    List<AuthorizationToken> findAllByUser(@NotNull User user);

}
