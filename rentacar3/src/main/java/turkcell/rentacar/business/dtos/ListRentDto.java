package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListRentDto {
	private int rentId;
	
	private LocalDate rentDate;
	
	private LocalDate returnDate;
	
	private int carId;
	
	private int customerId;

}
