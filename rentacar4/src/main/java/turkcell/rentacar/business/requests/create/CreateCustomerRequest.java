package turkcell.rentacar.business.requests.create;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
	@NotNull
	@Size(min=2,max=25)
	private String customerName;
	
	@Size(min=5,max=25)
	@NotNull
	private String customerPassword;
	
	@Size(min=5,max=25)
	@NotNull	
	private String customerEmail;

}
