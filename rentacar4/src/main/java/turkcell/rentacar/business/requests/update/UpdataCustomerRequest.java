package turkcell.rentacar.business.requests.update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdataCustomerRequest {
	@Size(min=5,max=25)
	@NotNull
	private String customerPassword;
	
	@Size(min=5,max=25)
	@NotNull	
	private String customerEmail;

}
