package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.Model.Prestamos;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPrestamoService {


    public Prestamos createPrestamo(Prestamos prestamos);

    public List<Prestamos> getListPrestamos(Date to, Date from);

    public Optional<Prestamos> getPrestamoById(long id);

    public double getBalance(Date date, String target);

}
