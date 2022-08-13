package MercadoLibrePrestamos.Prestamos.ApiRest;

import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaBalanceDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.RespuestaSolicitudDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.SolicitudPagoDTO;
import MercadoLibrePrestamos.Prestamos.DTOs.SolicitudPrestamoDTO;
import MercadoLibrePrestamos.Prestamos.Model.Pagos;
import MercadoLibrePrestamos.Prestamos.Model.Prestamos;
import MercadoLibrePrestamos.Prestamos.Model.Usuario;
import MercadoLibrePrestamos.Prestamos.Service.*;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public List<Prestamos> GetPrestamos (@RequestParam(name = "from") String from, @RequestParam(name = "to") String to, @RequestParam(name = "page", defaultValue = "0") int page) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Pageable pageRequest = PageRequest.of(page, 3);
        List<Prestamos> respuesta =  prestamoService.getListPrestamos(formato.parse(from),  formato.parse(to), pageRequest).getContent();

        return respuesta;


    }

    @PostMapping(path = "/pago")
    public ResponseEntity<Pagos> makePago(@RequestBody SolicitudPagoDTO solicitud){
        long loan_id = solicitud.getLoan_id();

       Optional<Prestamos> prestamo = prestamoService.getPrestamoById(loan_id);
        Pagos pago = new Pagos();


       if(!prestamo.isEmpty()){
           if(solicitud.getAmount() <= prestamo.get().getAmount()) {
               pago.setLoan_id(prestamo.get().getId());
               pago.setAmount(solicitud.getAmount());
               pago.setDebt(prestamo.get().getAmount());
               pago.setDate(new Date());


               pago = pagoService.doPago(pago);
           }else{
               return new ResponseEntity("el pago no puede ser mayor a la deduda", HttpStatus.BAD_REQUEST);
           }

       }else {
           return new ResponseEntity("No se encontro credito", HttpStatus.BAD_REQUEST);
       }



        return ResponseEntity.ok(pago);
    }

    @GetMapping(path = "/balanceByTarget")
    public ResponseEntity<RespuestaBalanceDTO> balance2 (@RequestParam(name = "date") String date, @RequestParam(name = "target") String target) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        RespuestaBalanceDTO respuesta = new RespuestaBalanceDTO();
        List<Prestamos> lista = new ArrayList<>();

        lista = prestamoService.getBalanceByDateAndTarget(formato.parse(date), target);
        double total=0;

        if(target.equals("REMIUM")  || target.equals("MEDIUM") || target.equals("NEW")|| target.equals("FREQUENT")){

            for(int i=0; i<lista.size();i++){
                total = total + lista.get(i).getAmount();
            }
            respuesta.setBalance(total);
            return ResponseEntity.ok(respuesta);

        }else{
            return new ResponseEntity("No se reconoce el Target", HttpStatus.BAD_REQUEST);
        }






    }
    @GetMapping(path = "/balance")
    public ResponseEntity<RespuestaBalanceDTO> balance (@RequestParam(name = "date") String date, @RequestParam(name = "target", defaultValue = "") String target) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        RespuestaBalanceDTO respuesta = new RespuestaBalanceDTO();
        List<Prestamos> lista = new ArrayList<>();

        if(target.equals("")){
            lista = prestamoService.getBalanceByDate(formato.parse(date));
        }else if (target.equals("REMIUM")  || target.equals("MEDIUM") || target.equals("NEW")|| target.equals("FREQUENT")){
            lista = prestamoService.getBalanceByDateAndTarget(formato.parse(date), target);
        }else{
            return new ResponseEntity("No se reconoce el Target", HttpStatus.BAD_REQUEST);
        }


        double total=0;

        for(int i=0; i<lista.size();i++){
            total = total + lista.get(i).getAmount();
        }
        respuesta.setBalance(total);


        return ResponseEntity.ok(respuesta);

    }




}
