package turkcell.rentacar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;
	
	@Column(name = "invoice_no")
	private int invoiceNo;
	
	@Column(name = "created_date")
	private LocalDate createdDate;
	
	@Column(name = "rental_days_value")
	private double rentalDaysValue;		
	
	@Column(name = "rental_date")
	private LocalDate rentalDate;	
	
	@Column(name = "return_date")
	private LocalDate returnDate;	
	
	@Column(name = "total_price")
	private double totalPrice;	
	
	@ManyToOne
	@JoinColumn(name = "customer_id" )	
	private Customer customer;		
	
	@ManyToOne	
	@JoinColumn(name ="rental_id")		
	private Rental rental;	
	
	
}
