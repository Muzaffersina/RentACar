package turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListPaymentInfoDto {
	
	private int paymentInfoId;	

	private String cardNo;	

	private String cVV;	

	private String year;	

	private String month;	

	private String name;

}
