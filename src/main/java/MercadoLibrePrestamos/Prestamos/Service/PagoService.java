package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.Model.Pagos;
import MercadoLibrePrestamos.Prestamos.Repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService implements IPagoService{
    @Autowired
    PagoRepository pagoRepository;

    @Override
    public Pagos doPago(Pagos pagos) {
        return pagoRepository.save(pagos);
    }
}
