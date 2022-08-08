package MercadoLibrePrestamos.Prestamos.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagos {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "loan_id")
    private long loan_id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "debt")
    private double debt;

    @Column(name = "date")
    private Date date;

}
