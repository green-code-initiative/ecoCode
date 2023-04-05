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
@Table(name = "purchaseOrder")
public class ForceLazyFetchTypeUseNonCompliant implements Serializable {
   
  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER) // Noncompliant {{Force the use of LAZY FetchType}}
  private Set<OrderItem> items = new HashSet<OrderItem>();

}
