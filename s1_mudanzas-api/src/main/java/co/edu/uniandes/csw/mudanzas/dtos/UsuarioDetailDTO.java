/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import co.edu.uniandes.csw.mudanzas.entities.TarjetaDeCreditoEntity;
import co.edu.uniandes.csw.mudanzas.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extiende de (@link UsuarioDTO) para manejar las relaciones. * Al
 * serializarse como JSON esta clase implementa el siguiente modelo: <br>
 * <pre>
 *   {
 *  "id": number,
 *  "login": string,
 *  "password": string,
 *  "nombre": string,
 *  "apellido": string,
 *  "correo": string,
 *  "ciudad": string,
 *  "tarjetas": [{@link TarjetaDeCreditoDTO}],
 *  "cargas": [{@link CargaDTO}],
 *  "subastas": [{@link SubastaDTO}]
 * }
 * </pre> Por ejemplo un usuario se representa asi:<br>
 *
 * <pre>
 *
 *   {
 *  "id": 1234,
 *  "login": "p.navas",
 *  "password": "123",
 *  "nombre": "Luis Miguel",
 *  "apellido": "Gomez",
 *  "correo": "lm.gomezl@uniandes.edu.co",
 *  "ciudad": "Manizales",
 *  "tarjetas": [
 *  {
 *      "id": 321,
 *      "nombreTarjeta": "LUIS MIGUEL GOMEZ L",
 *      "numeroSerial": 9876543251,
 *      "codigoSeguridad": 951,
 *      "titularCuenta": Luis Miguel Gomez Londono,
 *      "fechaVencimiento": "24/07/2019"
 *  }
 *  ],
 * "cargas": [
 *  {
 *      "datosEnvio": "Gran mudanza",
 *      "volumen": 152,
 *      "imagenes": [],
 *      "lugarSalida": "Cali, Colombia",
 *      "lugarLlegada": "Barranquilla, Colombia",
 *      "fechaEstimadaLlegada": "24/07/2019",
 *      "fechaEnvio": "27/07/2019",
 *      "observaciones": "Gran pedido objetos valor",
 *      "id": 456
 *  }
 *  ],
 * "subastas": [
 *  {
 *      "valorInicial": 120000,
 *      "valorFinal": 300000,
 *      "idSubasta": 741
 *  }
 *  ],
 * }
 *
 * </pre>
 *
 * @author Luis Miguel
 */
public class UsuarioDetailDTO extends UsuarioDTO {

    /*
    * Esta lista de tipo TarjetaDeCreditoDTO contiene las Tarjetas De Credito que estan asociados a un Usuario.
     */
    private List<TarjetaDeCreditoDTO> tarjetas;

    /*
    * Esta lista de tipo SubastaDTO contiene las subastas que estan asociadas a un Usuario.
     */
    private List<SubastaDTO> subastas;

    /*
    * Esta lista de tipo CargaDTO contiene las cargas que estan asociadas a un Usuario.
     */
    private List<CargaDTO> cargas;

    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO() {
    }

    /**
     * Constructor
     *
     * @param usuarioEntity
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        if (usuarioEntity != null) {

            if (usuarioEntity.getTarjetas() != null) {
                tarjetas = new ArrayList<>();
                for (TarjetaDeCreditoEntity tarjeta : usuarioEntity.getTarjetas()) {
                    tarjetas.add(new TarjetaDeCreditoDTO(tarjeta));
                }
            }

            if (usuarioEntity.getCargas() != null) {
                cargas = new ArrayList<>();
                for (CargaEntity carga : usuarioEntity.getCargas()) {
                    cargas.add(new CargaDTO(carga));
                }
            }

            if (usuarioEntity.getSubastas() != null) {
                subastas = new ArrayList<>();
                for (SubastaEntity subasta : usuarioEntity.getSubastas()) {
                    subastas.add(new SubastaDTO(subasta));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del usuario para transformar a Entity
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        if (tarjetas != null) {
            List<TarjetaDeCreditoEntity> tarjetasEntity = new ArrayList<>();
            for (TarjetaDeCreditoDTO dtoTarjeta : tarjetas) {
                tarjetasEntity.add(dtoTarjeta.toEntity());
            }
            usuarioEntity.setTarjetas(tarjetasEntity);
        }
        if (subastas != null) {
            List<SubastaEntity> subastasEntity = new ArrayList<>();
            for (SubastaDTO dtoSubasta : subastas) {
                subastasEntity.add(dtoSubasta.toEntity());
            }
            usuarioEntity.setSubastas(subastasEntity);
        }
        if (cargas != null) {
            List<CargaEntity> cargasEntity = new ArrayList<>();
            for (CargaDTO dtoCarga : cargas) {
                cargasEntity.add(dtoCarga.toEntity());
            }
            usuarioEntity.setCargas(cargasEntity);
        }
        return usuarioEntity;
    }
}
