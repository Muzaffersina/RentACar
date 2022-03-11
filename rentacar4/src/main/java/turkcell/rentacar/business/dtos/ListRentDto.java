package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentDto {
	private int rentalId;
	
	private LocalDate rentalDate;
	
	private LocalDate returnDate;
	
	private int carId;
	
	private int customerId;
	
	private int additionalId;
	private String additionalName;	
	

}
