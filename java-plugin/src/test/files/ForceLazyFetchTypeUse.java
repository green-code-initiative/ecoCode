@Entity
@Table(name = "purchaseOrder")
public class Order implements Serializable {
   
  @OneToMany(mappedBy = "order", fetch = FetchType.EAGER) // Noncompliant {{Force the use of LAZY FetchType}}
  private Set<OrderItem> items = new HashSet<OrderItem>();
   
  ...
   
}

@Entity
@Table(name = "purchaseOrder")
public class Order implements Serializable {
   
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY) // Ignored => compliant
  private Set<OrderItem> items = new HashSet<OrderItem>();
   
  ...
   
}
