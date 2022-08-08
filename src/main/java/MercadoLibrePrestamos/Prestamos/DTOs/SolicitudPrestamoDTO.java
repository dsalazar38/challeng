package MercadoLibrePrestamos.Prestamos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudPrestamoDTO {

    private double amount;
    private int term;
    private long user_id;

}
