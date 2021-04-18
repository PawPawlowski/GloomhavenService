package gloomhavenService.services.repository;

import org.springframework.data.jpa.domain.Specification;
import gloomhavenService.services.model.domain.Scenario;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ScenarioNumberSpecification implements Specification<Scenario> {

    private int number;

    private ScenarioNumberSpecification(int number) {
        this.number = number;
    }

    public static ScenarioNumberSpecification ofNumber(int number) {
        return new ScenarioNumberSpecification(number);
    }

    @Override
    public Predicate toPredicate(Root<Scenario> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("ref"), number);
    }
}
