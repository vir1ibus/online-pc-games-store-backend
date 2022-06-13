package org.vir1ibus.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.vir1ibus.onlinestore.entity.ConfirmationToken;

import javax.validation.constraints.NotBlank;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Integer> {

    ConfirmationToken findTokenByValue(@NotBlank String value);

}
