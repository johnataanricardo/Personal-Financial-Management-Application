package com.fean.seufinanceiro.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartDto {

    private List<FluxoDeCaixaMensalDto> fluxoDeCaixaMensalDtos;

    public ChartDto() {}

    public ChartDto(List<FluxoDeCaixaMensalDto> fluxoDeCaixaMensalDtos) {
        this.fluxoDeCaixaMensalDtos = fluxoDeCaixaMensalDtos;
    }

    public List<FluxoDeCaixaMensalDto> getFluxoDeCaixaMensalDtos() {
        if (this.fluxoDeCaixaMensalDtos == null)
            this.fluxoDeCaixaMensalDtos = new ArrayList<>();
        return fluxoDeCaixaMensalDtos;
    }
}
