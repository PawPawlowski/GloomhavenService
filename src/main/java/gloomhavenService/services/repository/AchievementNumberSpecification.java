package gloomhavenService.services.repository;

import gloomhavenService.services.model.domain.Achievement;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AchievementNumberSpecification implements Specification<Achievement> {

    private int number;

    private AchievementNumberSpecification(int number) {
        this.number = number;
    }

    public static AchievementNumberSpecification ofNumber(int number) {
        return new AchievementNumberSpecification(number);
    }

    @Override
    public Predicate toPredicate(Root<Achievement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("ref"), number);
    }
}
