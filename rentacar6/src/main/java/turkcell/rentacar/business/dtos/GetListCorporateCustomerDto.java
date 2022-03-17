package turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListCorporateCustomerDto {

	
	private int id;
	private String email;
	private String password;
	private String name;
	private int taxNumber;
}
