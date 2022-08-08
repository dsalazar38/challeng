package MercadoLibrePrestamos.Prestamos.Service;

import MercadoLibrePrestamos.Prestamos.Model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    public List<Usuario> getAll();

    public Optional<Usuario> getById(long id);

    public Usuario createUsuario(Usuario usuario);
}
