package turkcell.rentacar.business.requests.update;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {
	@NotNull
	@Positive
	private int rentalId;	
	
	@Positive
	@NotNull
	private int rentKm;
	
	@Positive
	@NotNull
	private int returnKm;
	
	@Positive
	@NotNull
	private int carId;
	
	@Positive
	@NotNull
	private int customerId;		
			
	private List<Integer> additionalIds;	
		
	@NotNull
	private LocalDate rentalDate;
	@NotNull
	private LocalDate returnDate;	
	
	@NotNull	
	private int rentalCityId;	
	
	@NotNull
	private int returnCityId;	
	
}
