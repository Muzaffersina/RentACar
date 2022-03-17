package turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id" , referencedColumnName = "id")
public class Customer extends User{
	
	@Column(name = "customer_id",insertable = false, updatable = false)
	private int customerId;
	
	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private List<Rental> rentals;
	
	
	@OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;
	
}
