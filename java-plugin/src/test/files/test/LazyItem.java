package fr.greencodeinitiative.java.checks.forcelazyfetchtypeuse;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LazyItem")
public class LazyItem implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
}
