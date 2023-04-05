import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "myTable")
public class ForceLazyFetchTypeUseAllInOne implements Serializable {
   
  @OneToMany(mappedBy = "myOrders", fetch = FetchType.EAGER) // Noncompliant {{Force the use of LAZY FetchType}}
  private Set<OrderItem> items = new HashSet<OrderItem>();

  @OneToMany(mappedBy = "myOrders", fetch = FetchType.LAZY)
  private Set<OrderItem> items = new HashSet<OrderItem>();

  @Column(name = "ORDER", length = 50, nullable = false, unique = false)
  private Set<OrderItem> items = new HashSet<OrderItem>();

}
