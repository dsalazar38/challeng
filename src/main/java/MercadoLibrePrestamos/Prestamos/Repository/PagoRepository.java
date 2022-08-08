package MercadoLibrePrestamos.Prestamos.Repository;

import MercadoLibrePrestamos.Prestamos.Model.Pagos;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends PagingAndSortingRepository<Pagos, Long> {
}
