/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.VehiculoPersistence;
import static java.lang.Character.isDigit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */
@Stateless
public class DiaLogic 
{
    /**
     * Inyección de la persistencia de un día.
     */
   @Inject
   private DiaPersistence per;
   
   /**
     * Inyección de la persistencia de un día.
     */
   @Inject
   private VehiculoPersistence vehiculoPersistence;
    /**
     * 
     * @param entity
     * @param placa
     * @return La entidad del dia creada, persistida y con las reglas de negocio validadas
     * @throws BusinessLogicException 
     */
    public DiaEntity crearDia(DiaEntity entity, String placa) throws BusinessLogicException
    {
        // Verificación que el vehiculo no sea nulo.
        VehiculoEntity vec = vehiculoPersistence.findByPlaca(placa);
        if(vec == null)
        {
            throw new BusinessLogicException("No existe ningun vehiculo con la placa: " + placa);
        }
        
        entity.setVehiculo(vec);
        
       
        
        //Verificaión de que solo existe un dia con ese id
        if(per.find(entity.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe una agenda con el id: \"" + entity.getId() + "\"");
        }
        // Verificación de nulidad para dia actual y vehiculo
        if(entity.getDiaActual() == null )
        {
            throw new BusinessLogicException("Ninguno de los campos puede ser nulo");
        }
        // verificación del formato del día actual
        if(! isValidDateFormat("dd/MM/yyyy", entity.getDiaActual().toString()))
        {
            throw new BusinessLogicException("El formato para el día actual no es valido");
        }
        
        
        
        //verificacion que el dia actual si corresponda con su valor real
        Date d =Calendar.getInstance(TimeZone.getDefault()).getTime();
        int añoP = d.getYear();
        int añoN = entity.getDiaActual().getYear();
        if(añoP!=añoN)
        {
            throw new BusinessLogicException("El dia actual no coincide con el verdadero dia actual");
        }
        // Verificación para que la horaiNICIO NO SEA MAYOR QUE LA HORAfIN
        if(!entity.getHoraInicio().before(entity.getHoraFin()) )
        {
          throw new BusinessLogicException("La hora inicial no puede ser mayor o igual a la hora final");
        }
         // Verificación que la hora inicial no sea mayor o igual a la final
        if(!entity.getHoraFin().after(entity.getHoraInicio()))
        {
          throw new BusinessLogicException("La hora final no puede menor o igual a la hora Inicial");
        }
        // Verificación de que el dia tenga un único vehículo
     //   if(per.findByVehiculo(entity.getVehiculo())!= null)
     //   {
     //       throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getVehiculo().getPlaca() + "\"");
     //   }
       
 
        
        entity = per.create(entity);
        return entity;
    }
    /**
     * Metodo auxiliar para revisar el formato de la fechaActual
     * @param formato
     * @param valor
     * @return 
     */
    private boolean isValidDateFormat(String formato, String valor)
    {
      boolean rta = false;
      if(valor == null)
      {
          rta = false;
      }
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);
        try
        {
            Date date = sdf.parse(valor);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            rta = false;
        }
        rta = true;
      return rta;
    }
    
    
    /**
     * Obtener un usuario por medio de su login.
     *
     * @param pId
     * @return el usuario solicitado por medio de su login.
     * @throws co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException
     */
    public DiaEntity getDia(long pId) throws BusinessLogicException {
        DiaEntity dEntity = per.find(pId);
        if (dEntity == null) {
            throw new BusinessLogicException("No existe tal usuario con login: " +pId);
        }
        return dEntity;
    }

    /**
     * Actualizar un usuario.
     *
     * @param nuevoDia: dia con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el dia con los cambios actualizados en la base de datos.
     */
    public DiaEntity updateDia(DiaEntity nuevoDia) {
        DiaEntity usuarioEntity = per.update(nuevoDia);
        return usuarioEntity;
    }
    /**
     * Metodo que encuentra el dia por medio de la placa de un vehiculo
     * @param idD
     * @param placa
     * @return
     * @throws BusinessLogicException 
     */
    public DiaEntity getDiaPlacaVehiculo(Long idD, String placa) throws BusinessLogicException
    {
        DiaEntity rta = null;
        VehiculoEntity vehiculo = vehiculoPersistence.findByPlaca(placa);
        DiaEntity diaEntity = per.find(idD);
        if(diaEntity.getVehiculo().getPlaca().equals(vehiculo.getPlaca()))
        {
            rta = diaEntity;
        }
        else
        {
            throw new BusinessLogicException("No existe tal dia con un vehiculo con placa: " + placa);

        }
        return rta;
    }

    /**
     * Borrar un usuario
     *
     * @param usuarioId: id del usuario a borrar
     * @throws BusinessLogicException Si el usuario a eliminar tiene tarjetas de
     * credito.
     */
    public void deleteDia(Long diaId) throws BusinessLogicException 
    {
        
        per.delete(diaId);
    }
    
    
}
