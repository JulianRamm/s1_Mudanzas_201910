/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.mudanzas.dtos;

import co.edu.uniandes.csw.mudanzas.entities.SubastaEntity;

/**
 *
 * @author estudiante
 */
public class SubastaDTO {

    private Long idSubasta;

    private double valorInicial;

    private double valorFinal;

    public SubastaDTO() {

    }

    public SubastaDTO(SubastaEntity subEntity) {
        if (subEntity != null) {
            setIdSubasta(subEntity.getId());
            setValorFinal(subEntity.getValorFinal());
            setValorInicial(subEntity.getValorInicial());
        }

    }

    public Long getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(Long idSubasta) {
        this.idSubasta = idSubasta;
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
        subEntity.setId(idSubasta);
        subEntity.setValorFinal(valorFinal);
        subEntity.setValorInicial(valorInicial);
        return subEntity;
    }

}
