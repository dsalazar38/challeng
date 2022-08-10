package MercadoLibrePrestamos.Prestamos.ApiRest;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaSolicitudDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.SolicitudPagoDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.SolicitudPrestamoDTO;
import MercadoLibrePrestamos.Prestamos.Model.Pagos;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import MercadoLibrePrestamos.Prestamos.Model.Usuario;
import MercadoLibrePrestamos.Prestamos.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiRest {
    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IPrestamoService prestamoService;

    @Autowired
    IPagoService pagoService;

    @PostMapping (path = "/createUsu")
    public Usuario getAllusuarios(@RequestBody Usuario usuario){
        return usuarioService.createUsuario(usuario);
    }

    @GetMapping(path = "/Ususarios")
    public List<Usuario> getAllusuarios(){
        return usuarioService.getAll();
    }

    @PostMapping(path = "/SolicitudPrestamo")
    public ResponseEntity<RespuestaSolicitudDTO>  solicitudPrestamo(@RequestBody SolicitudPrestamoDTO solicitud){
        Optional<Usuario> usuario = usuarioService.getById(solicitud.getUser_id());
        Prestamos prestamos = new Prestamos();
        RespuestaSolicitudDTO respuesta = new RespuestaSolicitudDTO();
        double rate = 0;
        String target="";
        double mensualidad = 0;
        if (usuario != null){
            if (usuario.get().getCantidad_prestamos() > 0 &&
                usuario.get().getCantidad_prestamos() < 2 &&
                usuario.get().getVolumen_prestamos() < 100000 &&
                solicitud.getAmount() <= 500000 ){
                rate =0.15;
                target="NEW";
            }else if (usuario.get().getCantidad_prestamos() >= 2 &&
                        usuario.get().getCantidad_prestamos() < 5 &&
                        usuario.get().getVolumen_prestamos() < 1000000 &&
                        usuario.get().getVolumen_prestamos() > 500000 &&
                        solicitud.getAmount() <= 1000000 ){
                rate =0.10;
                target="FREQUENT";

            }else if (usuario.get().getCantidad_prestamos() > 5 &&
                    usuario.get().getVolumen_prestamos() > 5000000 &&
                    solicitud.getAmount() <= 5000000 ){
                rate=0.05;
                target="PREMIUM";

            }else {
                return new ResponseEntity("No cumple con criterios de seleccion", HttpStatus.BAD_REQUEST);
            }
            double r = rate/12;
           // [ r + r / ( (1+r) ^ term - 1) ] x amount
            double elevado = Math.pow((1+r),solicitud.getTerm()-1);
            mensualidad = (r+r/(elevado))*solicitud.getAmount();
            prestamos.setAmount(solicitud.getAmount());
            prestamos.setTerm(solicitud.getTerm());
            prestamos.setRate(rate);
            prestamos.setUser_id(solicitud.getUser_id());
            prestamos.setTarget(target);
            prestamos.setDate(new Date());
        }
        prestamos = prestamoService.createPrestamo(prestamos);
        respuesta.setLoan_id(prestamos.getId());
        respuesta.setInstallment(mensualidad);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping(path = "/ListarPrestamos")
    public List<Prestamos> GetPrestamos (@RequestParam(name = "from") String from, @RequestParam(name = "to") String to ) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        return prestamoService.getListPrestamos(formato.parse(from),  formato.parse(to));
    }

    @PostMapping(path = "/pago")
    public Pagos makePago(@RequestBody SolicitudPagoDTO solicitud){
        long loan_id = solicitud.getLoan_id();

       Optional<Prestamos> prestamo = prestamoService.getPrestamoById(loan_id);
        Pagos pago = new Pagos();


       if(prestamo != null){
           pago.setLoan_id(prestamo.get().getId());
           pago.setAmount(solicitud.getAmount());
           pago.setDebt(prestamo.get().getAmount());
           pago.setDate(new Date());


        pago = pagoService.doPago(pago);

       }



        return pago;
    }




}
