package com.github.akozubperez.math.arch.common;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;
import lombok.NonNull;

public enum BaseSpecification {
    ; // without instances
        
    @SuppressWarnings("unchecked")
    public static <U, T, K> Specification<U> elements(SingularAttribute<U, T> attribute, Function<K, T> function, K... elements) {
        if (elements.length > 1) {
            return (root, query, cb) -> {
                CriteriaBuilder.In<T> in = cb.in(root.get(attribute));
                Arrays.stream(elements).forEach(element -> in.value(function.apply(element)));
                return in;
            };
        } else if (elements.length == 1) {
            return (root, query, cb) -> cb.equal(root.get(attribute), function.apply(elements[0]));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Specification<T> and(@NonNull Specification<T>... specifications) {
        if (specifications.length > 1) {
            return (root, query, cb)
                    -> cb.and(Stream.of(specifications)
                            .map(s -> s.toPredicate(root, query, cb))
                            .toArray(Predicate[]::new));
        } else if (specifications.length == 1) {
            return specifications[0];
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static <T, K> void checkArray(List<Specification<K>> list, T[] array, Function<T[], Specification<K>> function) {
        if (array != null && array.length > 0) {
            list.add(function.apply(array));
        }
    }

    public static <T, K> void checkElement(List<Specification<K>> list, T element, Function<T, Specification<K>> function) {
        if (element != null) {
            list.add(function.apply(element));
        }
    }
}
