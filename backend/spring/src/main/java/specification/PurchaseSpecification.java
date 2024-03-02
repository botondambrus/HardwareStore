package specification;

import edu.bbte.idde.abim2109.spring.model.Purchase;
import org.springframework.data.jpa.domain.Specification;

public class PurchaseSpecification {
    public static Specification<Purchase> nameEqual(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("name"), name);
        };
    }

    public static Specification<Purchase> quantityGreaterThanOrEq(Integer minQuantity) {
        return (root, query, criteriaBuilder) -> {
            if (minQuantity == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), minQuantity);
        };
    }

    public static Specification<Purchase> quantityLessThanOrEq(Integer maxQuantity) {
        return (root, query, criteriaBuilder) -> {
            if (maxQuantity == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), maxQuantity);
        };
    }

    public static Specification<Purchase> hardwareStoreIdEqual(Long hardwareStoreId) {
        return (root, query, criteriaBuilder) -> {
            if (hardwareStoreId == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.equal(root.get("hardwareStore").get("id"), hardwareStoreId);
        };
    }
}
