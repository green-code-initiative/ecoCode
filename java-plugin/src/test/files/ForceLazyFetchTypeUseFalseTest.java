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
public class ForceLazyFetchTypeUseFalseTest implements Serializable {
  
  @Column(name = "STUDENT_NAME", length = 50, nullable = false, unique = false)
  private Set<OrderItem> items = new HashSet<OrderItem>();

}
