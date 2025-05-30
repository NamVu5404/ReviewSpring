package com.NamVu.ReviewSpring.specification;

import com.NamVu.ReviewSpring.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.NamVu.ReviewSpring.specification.SearchOperation.ZERO_OR_MORE_REGEX;

public class UserSpecificationBuilder {

    public final List<SpecSearchCriteria> params;

    public UserSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public UserSpecificationBuilder with(String key, String operation, Object value, String prefix, String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public UserSpecificationBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
        SearchOperation oper = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (oper == SearchOperation.EQUALITY) {
            boolean startWithAsterisk = prefix != null && prefix.contains(ZERO_OR_MORE_REGEX);
            boolean endWithAsterisk = suffix != null && suffix.contains(ZERO_OR_MORE_REGEX);

            if (startWithAsterisk && endWithAsterisk) {
                oper = SearchOperation.CONTAINS;
            } else if (startWithAsterisk) {
                oper = SearchOperation.ENDS_WITH;
            } else if (endWithAsterisk) {
                oper = SearchOperation.STARTS_WITH;
            }
        }

        params.add(new SpecSearchCriteria(key, oper, value, orPredicate));
        return this;
    }

    public Specification<User> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<User> specification = new UserSpecification(params.getFirst());

        for (int i = 1; i < params.size(); i++) {
            specification = params.get(i).getOrPredicate()
                    ? Specification.where(specification).or(new UserSpecification(params.get(i)))
                    : Specification.where(specification).and(new UserSpecification(params.get(i)));
        }

        return specification;
    }
}
