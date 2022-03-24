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
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	@Positive
	private int customerId;
	
	@Size(min=1,max=20)
	@NotNull
	private String email;
	
	@Size(min=1,max=50)
	@NotNull
	private String name;	
	
	@NotNull
	@Positive
	private int taxNumber;
}
