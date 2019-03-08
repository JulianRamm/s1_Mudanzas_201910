/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.DiaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.DiaPersistence;
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
   @Inject
   private DiaPersistence per;
    
    public DiaEntity crearDia(DiaEntity entity) throws BusinessLogicException
    {
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
        // Verificación de que el dia tenga un único vehículo
     //   if(per.findByVehiculo(entity.getVehiculo())!= null)
     //   {
     //       throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getVehiculo().getPlaca() + "\"");
     //   }
        // Verificación que la hora inicial no sea mayor o igual a la final
        if(!entity.getHoraInicio().before(entity.getHoraFin()))
        {
          throw new BusinessLogicException("La hora inicial no puede ser mayor o igual a la hora final");

        }
        // Verificacion del formato correcto para la hora inicial
        if(!isValidTimeFormat(entity.getHoraInicio().toString()))
        {
            throw new BusinessLogicException("El formato para la hora inicial no es valido");
        }
         // Verificacion del formato correcto para la hora final
        if(!isValidTimeFormat(entity.getHoraFin().toString()))
        {
            throw new BusinessLogicException("El formato para la hora Final no es valido");
        }
        
        if(! isValidDateFormat("dd/MM/yyyy", entity.getDiaActual().toString()))
        {
            throw new BusinessLogicException("El formato para el día actual no es valido");
        }
        
        
        //verificacion que el dia actual si corresponda con su valor real
        Date d =Calendar.getInstance(TimeZone.getDefault()).getTime();
        if(!entity.getDiaActual().equals(d))
        {
            throw new BusinessLogicException("El dia actual no coincide con el verdadero dia actual");
        }
        
        
   
        
        entity = per.create(entity);
        return entity;
    }
    
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
    
    private boolean isValidTimeFormat(String valor)
    {
        boolean rta = false;
        String formato= "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern patern = Pattern.compile(formato);
        Matcher matcher = patern.matcher(valor);
        rta = matcher.matches();
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
     * @param nuevoUsuario: usuario con los cambios para ser actualizado, por
     * ejemplo el nombre.
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public DiaEntity updateDia(DiaEntity nuevoDia) {
        DiaEntity usuarioEntity = per.update(nuevoDia);
        return usuarioEntity;
    }

    /**
     * Borrar un usuario
     *
     * @param usuarioId: id del usuario a borrar
     * @throws BusinessLogicException Si el usuario a eliminar tiene tarjetas de
     * credito.
     */
    public void deleteUsuario(Long diaId) throws BusinessLogicException 
    {
        
        per.delete(diaId);
    }
    
    
}
