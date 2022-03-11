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
	private double dailyPrice;
	
	@NotNull
	@Positive
	private int modelYear;
	
	@NotNull
	@Size(min=1,max=200)
	private String description;	
	
	@NotNull
	@Positive
	private int brandId;
	
	@NotNull
	@Positive
	private int colorId;

}
