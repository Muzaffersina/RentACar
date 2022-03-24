package turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payments")

public class Payment {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;
	
	@Column(name = "total_payment")
	private double totalPayment;
	
	@Column(name = "payment_date")
	private LocalDate paymentDate;
	
	@ManyToOne
	@JoinColumn(name = "rental_id")	
	private Rental rental;	
	
	@OneToMany(mappedBy = "payment")
	private List<PaymentInfo> paymentInfos;	
}



//19- Magic stringlerden kurtulunuz. kendimiz yapalim
//20- Müşteriler kart bilgilerini kaydetmek isterse kart bilgileri ayrı bir tabloda tutulmalıdır. +++++
//21- Müşteriler arabayı belirtilenden geç teslim ederse kiralama günü kadar yeni ödeme alınır. Fark tutarı kadar yeni fatura kesilir.

//            Bu kurallar ek hizmetler için de geçerlidir.
