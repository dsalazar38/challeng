package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPrestamoService {


    public Prestamos createPrestamo(Prestamos prestamos);

    public List<Prestamos> getListPrestamos(Date to, Date from);

    public Optional<Prestamos> getPrestamoById(long id);

    public List<Prestamos> getBalanceByDate(Date date);

    public List<Prestamos> getBalanceByDateAndTarget(Date date, String target);


}
