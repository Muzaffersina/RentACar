package turkcell.rentacar.entities.concretes;

import java.util.List;

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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="city_id")
	private int cityId;
	
	@Column(name = "city_name")
	private String cityName;
	
	@OneToMany(mappedBy = "rentalCity")
	private List<Rental> rentalCities;
	
	@OneToMany(mappedBy = "returnCity")
	private List<Rental> returnCities;

}
