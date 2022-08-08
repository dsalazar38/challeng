package MercadoLibrePrestamos.Prestamos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudPagoDTO {

    public long loan_id;
    public double amount;
}
