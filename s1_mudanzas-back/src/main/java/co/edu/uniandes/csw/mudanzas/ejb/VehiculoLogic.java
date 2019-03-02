/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.VehiculoEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.VehiculoPersistence;
import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Samuel Bernal Neira
 */

@Stateless
public class VehiculoLogic 
{
    @Inject
   private VehiculoPersistence per;
    
    public VehiculoEntity crearVehiculo(VehiculoEntity entity) throws BusinessLogicException
    {
        //Verificacion de un vehiculo con id unico.
        if(per.find(entity.getId())!= null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con el id: \"" + entity.getId() + "\"");
        }
        //Verificacion de un vehiculo con id unico.
        if(per.findByPlaca(entity.getPlaca())!=null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con la placa: \"" + entity.getPlaca() + "\"");
        }
        //Verificacion de un vehiculo con id unico.
        if(per.findByUbicacionActual(entity.getUbicacionActual())!= null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con el id de ubicación actual: \"" + entity.getUbicacionActual().getIdPar() + "\"");
        }
        //Verificación de un vehiculo con una agenda única.
        if(per.findByDia(entity.getAgenda())!= null)
        {
            throw new BusinessLogicException("Ya existe un vehiculo con la agenda: \"" + entity.getAgenda().getId() + "\"");

        }
        //Verificación que marca no tenga carácteres especiales, no sea vacia y su longitud sea menor a 25 carácteres
        boolean marcaCorrectFormat= false;
        char[]caracteresEspeciales = "!#$%&/()=?¡¿_{}´+¨*~[]".toCharArray();
        if(entity.getMarca().equals(""))
        {
            marcaCorrectFormat = false;
        }
        else
        {
            marcaCorrectFormat = true;
        }
        for(int i = 0; i< caracteresEspeciales.length ; i++)
        {
            if(entity.getMarca().indexOf(caracteresEspeciales[i])>0)
            {
                marcaCorrectFormat =false;
            }
            else
            {
                marcaCorrectFormat = true;
            }
        }
        if(!marcaCorrectFormat || entity.getMarca().length()>25)
        {
            throw new BusinessLogicException("La marca: \"" + entity.getMarca() + "no tiene un formato valido\"");
        }
        
        //Verificación que el color tenga carácteres especiales, no sea vacia y su longitud sea menor a 25 carácteres
        boolean colorCorrectFormat= false;
        if(entity.getMarca().equals(""))
        {
            colorCorrectFormat = false;
        }
        for(int i = 0; i< caracteresEspeciales.length ; i++)
        {
            if(entity.getColor().indexOf(caracteresEspeciales[i])>0)
            {
                colorCorrectFormat =false;
            }
            else
            {
                colorCorrectFormat = true;
            }
        }
        if(!colorCorrectFormat || entity.getColor().length()>25)
        {
            throw new BusinessLogicException("El color: \"" + entity.getColor() + "no es valida\"");
        }
        
        
        //Verificación de nulidad para marca, color, agenda, placa y ubicaciónActual
        if(entity.getMarca()== null || entity.getColor() == null|| entity.getAgenda()== null||entity.getPlaca()==null||entity.getUbicacionActual()==null)
        {
            throw new BusinessLogicException("Ninguno de los campos puede ser nulo");
        }
        
        //Verificación del numero de conductores posibles a los que un vehículo puede estar adscrito.
        if(entity.getNumeroConductores()<0 || entity.getNumeroConductores()>8)
        {
            throw new BusinessLogicException("El número de conductores: \"" + entity.getNumeroConductores() + "No es valido \"");
        }
        
        //Verificación del formato de la placa
        char[]cadenaPlaca = entity.getPlaca().toCharArray();
        boolean placaCorrectFormat = false;
        for(int i = 0; i< cadenaPlaca.length;i++)
        {
            if(i <= 2)
            {
                if (isUpperCase(cadenaPlaca[i]))
                {
                    placaCorrectFormat = true;
                }
                else
                {
                    placaCorrectFormat = false;
                }
            }
            if(i>2)
            {
                if(isDigit(cadenaPlaca[i]))
                {
                    placaCorrectFormat = true;
                }
                else
                {
                    placaCorrectFormat = false;
                }
            }
        }
        if(!placaCorrectFormat || entity.getPlaca().length()>6)
        {
            throw new BusinessLogicException("La placa: \"" + entity.getPlaca() + "no tiene un formato valido\"");
        }
        
        
        entity = per.create(entity);
        return entity;
    }
    
  //  public boolean marcaSinCaracteresEspeciales()
  //  {
        
  //  }
    
}
