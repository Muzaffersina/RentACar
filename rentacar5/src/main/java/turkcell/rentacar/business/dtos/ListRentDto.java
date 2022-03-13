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
	private int carId;
	private int dailyPrice;
	private double totalPrice;

	private int userId;
	private String userName;

	private int additionalId;
	private String additionalName;

	private LocalDate rentalDate;

	private LocalDate returnDate;
	
	private String rentalCity;
	private String returnCity;

}
