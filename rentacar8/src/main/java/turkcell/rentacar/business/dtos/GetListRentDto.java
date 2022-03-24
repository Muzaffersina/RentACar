package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListRentDto {

	private int rentalId;
	private int carId;
	private int dailyPrice;
	private int rentKm;	
	private  int returnKm;
	private double totalPrice;

	private int customerId;
	
	private LocalDate rentalDate;
	private LocalDate returnDate;
	
	private String rentalCity;
	private String returnCity;	

}
