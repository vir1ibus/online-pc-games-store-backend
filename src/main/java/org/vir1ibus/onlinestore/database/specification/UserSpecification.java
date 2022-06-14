package org.vir1ibus.onlinestore.database.specification;

import org.vir1ibus.onlinestore.database.entity.Item_;
import org.vir1ibus.onlinestore.database.entity.User_;

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
