package turkcell.rentacar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdditionalRequest {
	
	
	@NotNull
	@Size(min = 1 , max = 25)
	private String additionalName;

	@NotNull
	@Positive
	private int additionalPrice;	
	
	
	
}
