package MercadoLibrePrestamos.Prestamos.Repository;

import MercadoLibrePrestamos.Prestamos.Model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
