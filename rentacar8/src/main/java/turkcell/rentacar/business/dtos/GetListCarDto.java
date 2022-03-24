package turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListCarDto {
	private int carId;	
	private double dailyPrice;
	private int modelYear;
	private int carTotalKm;
	private String description;
	
	private String brandName;
	private String colorName;


}
