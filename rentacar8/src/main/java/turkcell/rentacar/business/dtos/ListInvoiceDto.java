package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInvoiceDto {
	
	private int invoiceId;
	private int invoiceNo;
	private int customerId;
	private int rentalRentalId;
	

	private LocalDate rentalDate;		
	private LocalDate returnDate;	
	private LocalDate createdDate;	

	private long rentalDaysValue;
	private long totalPrice;

}
