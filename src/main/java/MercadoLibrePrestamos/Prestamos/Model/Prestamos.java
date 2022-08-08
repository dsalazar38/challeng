package MercadoLibrePrestamos.Prestamos.Model;
//Modelo de la tabla de prestamos


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prestamos")
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "term")
    private int term;

    @Column(name = "rate")
    private double rate;

    @Column(name = "user_id")
    private long User_id;

    @Column(name = "target")
    private String target;

    @Column(name = "date")
    private Date date;



}
