package turkcell.rentacar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarDamageRequest {

	@NotNull
	@Positive
	private int damageId;

	@NotNull
	@Size(min = 1)
	private String damageInfo;
	
	@NotNull
	private LocalDate localDate;
	
	@NotNull
	@Positive
	private int carId;


}
