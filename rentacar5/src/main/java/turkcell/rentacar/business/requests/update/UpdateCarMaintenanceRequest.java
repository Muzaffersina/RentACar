package turkcell.rentacar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {
	
	@NotNull
	@Positive
	private int maintenanceId;
	
	@NotNull
	@Positive
	private int carId;

	@NotNull
	@Size(min=1,max=200)
	private String description;
	
	@NotNull
	private LocalDate returnDate;
	
}
