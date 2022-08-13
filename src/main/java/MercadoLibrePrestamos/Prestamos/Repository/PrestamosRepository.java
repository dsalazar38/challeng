package MercadoLibrePrestamos.Prestamos.Repository;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrestamosRepository extends PagingAndSortingRepository<Prestamos, Long> {


    @Query("select u from Prestamos u where u.date between :from and :to")
    public Page<Prestamos> getListPrestamos(Date from, Date to, Pageable pageable);




    @Query("select u from Prestamos u where u.date < :dateTo")
    public List<Prestamos> getBalanceByDate(Date dateTo);

    @Query("select u from Prestamos u where u.date < :dateTo and u.target = :target")
    public List<Prestamos> getBalanceByDateAndTarget(Date dateTo, String target);
}
