package turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import turkcell.rentacar.entities.concretes.PaymentInfo;

public interface PaymentInfoDao extends JpaRepository<PaymentInfo, Integer> {

}
