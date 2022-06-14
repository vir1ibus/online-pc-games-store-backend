package org.vir1ibus.onlinestore.specification;

import org.springframework.data.jpa.domain.Specification;
import org.vir1ibus.onlinestore.entity.*;
import org.vir1ibus.onlinestore.utils.StringFormatter;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

public class ItemSpecification {

    private static Predicate predicate;
    private static CriteriaBuilder criteriaBuilder;

    public static void and (Predicate and) {
        predicate = criteriaBuilder.and(predicate, and);
    }

    public static Specification<Item> filters(final Map<String, String[]> filters) {
        return (root, query, cb) -> {
            predicate = cb.conjunction();
            criteriaBuilder = cb;
            query.distinct(true);
            filters.forEach((key, values) -> {
                switch (key) {
                    case "genre":
                        for (Integer genreId : StringFormatter.toIntegerList(values)) {
                            and(cb.in(root.join(Item_.GENRES).get(Genre_.ID)).value(genreId));
                        }
                        break;

                    case "item-type":
                        and(root.get(Item_.ITEM_TYPE).get(ItemType_.ID).in(StringFormatter.toIntegerList(values)));
                        break;

                    case "service-activation":
                        and(root.get(Item_.SERVICE_ACTIVATION).get(ServiceActivation_.ID).in(StringFormatter.toIntegerList(values)));
                        break;

                    case "search":
                        and(cb.like(root.get(Item_.TITLE), "%" + values[0] + "%"));
                        break;

                    case "publisher":
                        and(root.get(Item_.PUBLISHER).get(User_.ID).in(StringFormatter.toIntegerList(values)));
                        break;

                    case "developer":
                        and(root.get(Item_.DEVELOPER).get(User_.ID).in(StringFormatter.toIntegerList(values)));
                        break;

                    case "min-price":
                        and(cb.greaterThanOrEqualTo(root.get(Item_.RESULT_PRICE), StringFormatter.toIntegerList(values).get(0)));
                        break;

                    case "max-price":
                        and(cb.lessThanOrEqualTo(root.get(Item_.RESULT_PRICE), StringFormatter.toIntegerList(values).get(0)));
                        break;

                    case "sort":
                        switch (values[0]) {
                            case "price-asc" -> query.orderBy(cb.asc(root.get(Item_.RESULT_PRICE)));
                            case "price-desc" -> query.orderBy(cb.desc(root.get(Item_.RESULT_PRICE)));
                            case "title-asc" -> query.orderBy(cb.asc(root.get(Item_.TITLE)));
                            case "discount-desc" -> query.orderBy(cb.desc(root.get(Item_.DISCOUNT)));
                        }
                        break;
                    case "region-activation":
                        and(root.get(Item_.REGION_ACTIVATION).get(RegionActivation_.ID).in(StringFormatter.toIntegerList(values)));
                        break;
//                    case "available": // FIX
//                        if(Integer.parseInt(values[0]) > 0) {
//                            and(root.join(Item_.ACTIVATE_KEYS).get(ActivateKey_.PURCHASE).isNull());
//                        } else {
//                            Join<ActivateKey, Item> activateKey = root.join(Item_.ACTIVATE_KEYS);
//                            activateKey.on(activateKey.get(ActivateKey_.PURCHASE).isNull());
//                            and(cb.or(cb.isNull(activateKey), cb.isEmpty(root.get(Item_.ACTIVATE_KEYS))));
//                        }
//                        break;
                }
            });
            return cb.and(predicate);
        };
    }
}
