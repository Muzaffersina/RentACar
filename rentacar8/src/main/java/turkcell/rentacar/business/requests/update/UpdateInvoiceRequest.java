package turkcell.rentacar.business.requests.update;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	@Positive
	private int invoiceId;	
	@NotNull
	@Positive
	private int invoiceNo;
	@NotNull
	@Positive
	private int customerId;
	@NotNull
	@Positive
	private int rentalId;
	
	@NotNull
	private LocalDate rentalDate;
	@NotNull
	private LocalDate returnDate;
	@NotNull
	private LocalDate createdDate;

	
	@NotNull
	@Positive
	private long rentalDaysValue;
	
	@NotNull
	@Positive
	private double totalPrice;

}
