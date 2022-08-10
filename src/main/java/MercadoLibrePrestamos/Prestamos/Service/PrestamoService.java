package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import MercadoLibrePrestamos.Prestamos.Repository.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService implements IPrestamoService {
    @Autowired
    PrestamosRepository prestamosRepository;

    @Override
    public Prestamos createPrestamo(Prestamos prestamos) {
        return prestamosRepository.save(prestamos);
    }

    @Override
    public List<Prestamos> getListPrestamos(Date from, Date to) {
        return prestamosRepository.getListPrestamos(from ,to);
    }

    @Override
    public Optional<Prestamos> getPrestamoById(long id) {
        return prestamosRepository.findById(id);
    }




}
