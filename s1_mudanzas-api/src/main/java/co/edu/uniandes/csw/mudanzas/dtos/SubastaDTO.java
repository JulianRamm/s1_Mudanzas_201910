/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;
import java.io.Serializable;

/**
 *
 * @author estudiante
 */
public class SubastaDTO implements Serializable {

    private Long id;

    private double valorInicial;

    private double valorFinal;

    public SubastaDTO() {

    }

    public SubastaDTO(SubastaEntity subEntity) {
        if (subEntity != null) {
            setId(subEntity.getId());
            setValorFinal(subEntity.getValorFinal());
            setValorInicial(subEntity.getValorInicial());
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public SubastaEntity toEntity() {
        SubastaEntity subEntity = new SubastaEntity();
        subEntity.setId(id);
        subEntity.setValorFinal(valorFinal);
        subEntity.setValorInicial(valorInicial);
        return subEntity;
    }

}
