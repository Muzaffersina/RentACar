package turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rental_id")
	private int rentalId;		

	@Column(name="rental_date")
	private LocalDate rentalDate;
	
	@Column(name="return_date")
	private LocalDate returnDate;	

	
	@Column(name = "total_price")
	private double totalPrice;
	
	
	
	@ManyToOne
	@JoinColumn(name ="car_id")
	private Car car;	
	
	@ManyToOne
	@JoinColumn(name="additional_id")	
	private Additional additional;			
	
	@ManyToOne
	@JoinColumn(name ="rental_city_name")
	private City rentalCity;
	
	@ManyToOne
	@JoinColumn(name ="return_city_name")
	private City returnCity;	
	 
	@ManyToOne
	@JoinColumn(name ="id")
	private User user;
	
	
	@OneToMany(mappedBy ="rental")
	private List<OrderedAdditional> orderedAdditional;	
}

//gunluk kira ucreti
//	Arabalar kiralanırken farklı bir şehire bırakılabilir
//	Kiralamaya 750 TL eklenir     

//Şirketimiz büyüdü. Kurumsal müşteriler araba kiralayabilmelidir. (Kurumsal 
//Müşteri – vergiNo, Şirket ismi,email….
//Kiralama kuralları bireysel müşteri ile aynıdır.
//IndividualCustomer -- CorporateCustomer



