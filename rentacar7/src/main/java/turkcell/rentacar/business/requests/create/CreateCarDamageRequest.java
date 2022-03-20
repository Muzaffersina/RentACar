package turkcell.rentacar.business.requests.create;

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
public class CreateCarDamageRequest {
	
	@NotNull
	@Size(min = 1)
	private String damageInfo;
	
	@NotNull
	private LocalDate localDate;
	
	@NotNull
	@Positive
	private int carId;

}
