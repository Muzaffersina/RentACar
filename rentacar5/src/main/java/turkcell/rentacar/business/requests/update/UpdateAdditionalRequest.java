package turkcell.rentacar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalRequest {
	
	@NotNull
	@Positive
	private int additionalServiceId;	
	
	@NotNull
	@Size(min = 1 , max = 25)
	private String serviceName;

	@NotNull
	@Positive
	private double additionalPrice;
	
}
