package turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListCarDamageDto {
	
	private int damageId;
	private LocalDate damageDate;
	private String damageInfo;
	private int carId;

}
