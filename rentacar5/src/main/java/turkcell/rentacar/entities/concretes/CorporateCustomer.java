package turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="corporate_customers")
@PrimaryKeyJoinColumn(name = "corporate_id" , referencedColumnName = "id")
public class CorporateCustomer extends User{
	
	
	@Column(name="corporate_id")
	private int id;
	
	@OneToMany(mappedBy = "corporateCustomer")
	private List<Rental> rentals;

}
