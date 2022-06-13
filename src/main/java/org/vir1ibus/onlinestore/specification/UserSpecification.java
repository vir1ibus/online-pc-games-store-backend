package org.vir1ibus.onlinestore.specification;

import org.springframework.data.jpa.domain.Specification;
import org.vir1ibus.onlinestore.entity.Item_;
import org.vir1ibus.onlinestore.entity.User;
import org.vir1ibus.onlinestore.entity.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class UserSpecification {

    private static Predicate predicate;
    private static CriteriaBuilder criteriaBuilder;

    public static Predicate and (Predicate and) {
        predicate = criteriaBuilder.and(predicate, and);
        return predicate;
    }
}
