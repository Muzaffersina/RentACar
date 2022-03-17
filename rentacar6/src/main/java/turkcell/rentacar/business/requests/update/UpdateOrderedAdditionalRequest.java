package turkcell.rentacar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalRequest {
	
	@NotNull
	@Positive
	private int orderedId;	
	
	@NotNull
	@Positive
	private int rentalId;
	
	@NotNull
	@Positive
	private int additionalId;

}
