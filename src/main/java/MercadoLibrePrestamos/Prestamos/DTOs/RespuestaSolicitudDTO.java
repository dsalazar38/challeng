package MercadoLibrePrestamos.Prestamos.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaSolicitudDTO {

    private long loan_id;

    private double installment;
}
