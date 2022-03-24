package turkcell.rentacar.business.requests.update;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import turkcell.rentacar.entities.concretes.PaymentInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentRequest {
	
	@NotNull
	@Positive
	private int paymentId;
	
	@NotNull
	private LocalDate paymentDate;
	
	@NotNull
	@Positive
	private int rentalId;
	
	private PaymentInfo paymentInfo; 	

}
