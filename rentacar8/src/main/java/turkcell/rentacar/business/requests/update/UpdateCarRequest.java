package turkcell.rentacar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@Positive
	private int carTotalKm;
	
	@NotNull
	@Positive
	private int dailyKm;	

	@NotNull
	@Positive
	private int modelYear;

	@NotNull
	@Positive
	private int brandId;

	@NotNull
	@Positive
	private int colorId;

	@NotNull
	@Positive
	private double dailyPrice;

	@NotNull
	@Size(min = 1, max = 200)
	private String description;

}
