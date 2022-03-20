package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListPaymentDto {
	
	
	private int paymentId;	
	private double totalPayment;	
	private LocalDate paymentDate;
	private int rentalRentalId;

}
