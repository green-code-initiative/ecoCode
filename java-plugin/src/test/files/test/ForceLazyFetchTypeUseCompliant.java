package fr.greencodeinitiative.java.checks.forcelazyfetchtypeuse;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPLIANT")
public class ForceLazyFetchTypeUseCompliant extends LazyItem {
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
}
