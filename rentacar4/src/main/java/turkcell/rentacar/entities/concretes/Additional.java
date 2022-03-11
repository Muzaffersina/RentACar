package turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "additional_services")
public class Additional {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "additional_id")
	private int additionalId;
	
	@Column(name = "additional_name")
	private String additionalName;
	
	@Column(name = "price") 
	private int additionalPrice;	
	
	@OneToMany(mappedBy = "additional", cascade = CascadeType.ALL)			// MAPPEDBY HATA VERDİRİYOR..
	private List<Rental> rentals;	
}
