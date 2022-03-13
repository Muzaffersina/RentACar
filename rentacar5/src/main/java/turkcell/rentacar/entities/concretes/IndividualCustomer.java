package turkcell.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="individual_customers")
@PrimaryKeyJoinColumn(name = "individual_id" , referencedColumnName = "id")

public class IndividualCustomer extends User{ 	
	
	@Column(name="individual_id")
	private int id;
	
	
	
		
	//User nesnesi oluşturup customerde extend et..
}	

