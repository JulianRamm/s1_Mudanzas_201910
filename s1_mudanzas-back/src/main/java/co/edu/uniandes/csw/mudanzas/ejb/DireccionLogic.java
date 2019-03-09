/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.CargaEntity;
import co.edu.uniandes.csw.mudanzas.entities.DireccionEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.CargaPersistence;
import co.edu.uniandes.csw.mudanzas.persistence.DireccionPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author je.osorio
 */
@Stateless
public class DireccionLogic {

    @Inject
    DireccionPersistence persistencia;
    @Inject
    CargaPersistence cargaP;

    public DireccionEntity createDireccion(DireccionEntity dir, Long idCarga) throws BusinessLogicException {
        CargaEntity carga = cargaP.find(idCarga);
        if (carga == null) {
            throw new BusinessLogicException("La carga tiene que existir");
        }
        carga.getDirecciones().add(dir);
        dir.setCarga(carga);
        persistencia.create(dir);
        cargaP.update(carga);
        return dir;
    }
    
    public List<DireccionEntity> findAll() throws BusinessLogicException{
       List<DireccionEntity> ents= persistencia.findAll();
       if(ents==null){
           throw new BusinessLogicException("no hay dirs");
       }
       return ents;
    }
    public DireccionEntity find(Long id) throws BusinessLogicException{
        DireccionEntity ents= persistencia.find(id);
       if(ents==null){
           throw new BusinessLogicException("no hay dir");
       }
       return ents;
    }
}
