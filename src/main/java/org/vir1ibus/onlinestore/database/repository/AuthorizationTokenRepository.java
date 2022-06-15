package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vir1ibus.onlinestore.database.entity.AuthorizationToken;
import org.vir1ibus.onlinestore.database.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AuthorizationTokenRepository extends CrudRepository<AuthorizationToken, Integer> {
    AuthorizationToken findTokenByValue(@NotBlank String value);

    AuthorizationToken findTokenByValueAndActiveIsTrue(@NotBlank String value);
    List<AuthorizationToken> findAllByUser(@NotNull User user);

    List<AuthorizationToken> findAllByUserAndActiveIsTrue(@NotNull User user);
}
