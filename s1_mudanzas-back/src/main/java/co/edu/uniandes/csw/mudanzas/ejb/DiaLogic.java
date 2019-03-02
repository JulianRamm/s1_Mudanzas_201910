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
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */
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
        if(entity.getDiaActual() == null || entity.getVehiculo() == null)
        {
            throw new BusinessLogicException("Ninguno de los campos puede ser nulo");
        }
        // Verificación de que el dia tenga un único vehículo
        if(per.findByVehiculo(entity.getVehiculo())!= null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getVehiculo().getPlaca() + "\"");
        }
        // Verificación que la hora inicial no sea mayor o igual a la final
        if(entity.getHoraInicio().compareTo(entity.getHoraFin())>=0)
        {
          throw new BusinessLogicException("La hora inicial no puede ser mayor o igual a la hora final");

        }
         // Verificacion del formato correcto para la hora inicial
        boolean formatoCorrectoHI = true;
        char[] cadenaHI = entity.getHoraInicio().toString().toCharArray();
        for(int i = 0; i < cadenaHI.length && formatoCorrectoHI; i++)
        {
            if(i<2||i>2||i>5)
            {
                if(isDigit(cadenaHI[i]))
                {
                    formatoCorrectoHI = true;
                }
                else
                {
                    formatoCorrectoHI = false;
                }
            }
            if((i==2 || i ==5) && (":".equals(cadenaHI[i])) )
            {
                formatoCorrectoHI = true;
            }
            else 
            {
                formatoCorrectoHI = false;
            }
        }
        if(!formatoCorrectoHI || cadenaHI.length > 8)
        {
            throw new BusinessLogicException("El formato para la hora inicial no es valido");
        }
        // Verificacion del formato correcto para la hora final
        boolean formatoCorrectoHF = true;
        char[] cadenaHF = entity.getHoraFin().toString().toCharArray();
        for(int i = 0; i < cadenaHF.length && formatoCorrectoHF; i++)
        {
            if(i<2||i>2||i>5)
            {
                if(isDigit(cadenaHF[i]))
                {
                    formatoCorrectoHF= true;
                }
                else
                {
                    formatoCorrectoHF = false;
                }
            }
            if((i==2 || i ==5) && (":".equals(cadenaHF[i])) )
            {
                formatoCorrectoHF = true;
            }
            else 
            {
                formatoCorrectoHF = false;
            }
        }
        if(!formatoCorrectoHF || cadenaHF.length > 8)
        {
            throw new BusinessLogicException("El formato para la hora final no es valido");
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
    
    
}
