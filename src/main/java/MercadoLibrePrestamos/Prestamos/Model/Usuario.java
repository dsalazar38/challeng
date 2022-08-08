package MercadoLibrePrestamos.Prestamos.Model;
//Modelo de la tabla usuarios

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "identificacion")
    private String identificacion;

    @Column(name = "cantidad_prestamos")
    private int cantidad_prestamos;

    @Column(name = "volumen_prestamos")
    private int volumen_prestamos;

    @Column(name = "fecha_creacion")
    private Date fecha_creacion;





}
