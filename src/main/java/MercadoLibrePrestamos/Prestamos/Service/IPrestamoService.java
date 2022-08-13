package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPrestamoService {


    public Prestamos createPrestamo(Prestamos prestamos);

    public Page<Prestamos> getListPrestamos(Date to, Date from, Pageable pageable);

    public Optional<Prestamos> getPrestamoById(long id);

    public List<Prestamos> getBalanceByDate(Date date);

    public List<Prestamos> getBalanceByDateAndTarget(Date date, String target);


}
