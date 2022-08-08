package MercadoLibrePrestamos.Prestamos.Repository;

import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrestamosRepository extends PagingAndSortingRepository<Prestamos, Long> {

   // @Query("select id, amount, term, rate, user_id, target, date from prestamos p where date between" )
    @Query("select u from Prestamos u where u.date between :from and :to")
    public List<Prestamos> getListPrestamos(Date from, Date to);


    @Query("select sum(u.amount) from Prestamos u where u.date > :data and u.target = :target")
    public double getBalance(Date date, String target);
}
