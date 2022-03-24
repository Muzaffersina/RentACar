package turkcell.rentacar.business.requests.update;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentInfoRequest {
	
	@NotNull
	private int paymentInfoId;
	
	@NotNull
	private String cardNo;
	
	@NotNull
	private String cVV;
	
	@NotNull
	private String year;
	
	@NotNull
	private String month;
	
	@NotNull
	private String name;

}
