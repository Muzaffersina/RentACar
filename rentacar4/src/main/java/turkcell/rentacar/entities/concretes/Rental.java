package turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rentals")
@Inheritance(strategy = InheritanceType.JOINED)
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rental_id")
	private int rentalId;		

	@Column(name="rental_date")
	private LocalDate rentalDate;
	
	@Column(name="return_date")
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="customer_id" )
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="additional_id")
	private Additional additional;	
	
	@OneToMany
	private List<OrderedAdditional> orderedAdditional;
	
}
