/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.ejb;

import co.edu.uniandes.csw.mudanzas.entities.OfertaEntity;
import co.edu.uniandes.csw.mudanzas.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.mudanzas.persistence.OfertaPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Andres Gonzalez
 */
@Stateless
public class OfertaLogic {

    @Inject
    private OfertaPersistence persistence;

    public OfertaEntity createOferta(OfertaEntity oferEntity) throws Exception {

        if (persistence.find(oferEntity.getId()) != null) {
            throw new BusinessLogicException("la oferta con id: " + oferEntity.getId() + "ya existe");

        }
        return persistence.create(oferEntity);
    }

}
