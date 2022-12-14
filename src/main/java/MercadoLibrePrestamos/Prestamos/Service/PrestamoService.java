package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import MercadoLibrePrestamos.Prestamos.Repository.PrestamosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Prestamos> getListPrestamos(Date from, Date to, Pageable pageable) {
        return prestamosRepository.getListPrestamos(from ,to, pageable);
    }


    @Override
    public Optional<Prestamos> getPrestamoById(long id) {
        return prestamosRepository.findById(id);
    }

    @Override
    public List<Prestamos> getBalanceByDate(Date date) {
        return prestamosRepository.getBalanceByDate(date);
    }

    @Override
    public List<Prestamos> getBalanceByDateAndTarget(Date date, String target) {
        return prestamosRepository.getBalanceByDateAndTarget(date,target);
    }


}
