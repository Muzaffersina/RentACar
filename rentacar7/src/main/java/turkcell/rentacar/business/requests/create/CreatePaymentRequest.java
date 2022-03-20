package turkcell.rentacar.business.requests.create;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {		

	
	@NotNull
	private LocalDate paymentDate;
	
	@NotNull
	@Positive
	private int rentalId;
	
	private String cardNo; 
	private String year;
	private String mounth;
	private String cVV;
	private double amount;
	

}
