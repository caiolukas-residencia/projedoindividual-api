package org.serratec.projetoindividual.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record VeiculoCriar(
    @Schema(description = "Identificador do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "ID é obrigatório")
    UUID idCliente,

    @Schema(description = "Marca que distribui o carro", example = "Toyota")
    @NotBlank(message = "Marca obrigatória")
    String marca,

    @Schema(description = "Modelo do carro", example = "Corola")
    @NotBlank(message = "Modelo é obrigatório")
    String modelo,

    @Schema(description = "Ano de lançamento do carro", example = "1980")
    @Min(value = 1900, message = "Ano inválido")
    int ano,

    @Schema(description = "Valor do carro", example = "200000.0")
    @Min(value = 1, message = "O valor deve ser maior que zero")
    double valor,

    @Schema(description = "Placa de identificação do carro", example = "ABC1D23")
    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres")
    @Pattern(regexp = "^[A-Z]{3}\\d[A-Z]\\d{2}$", message = "Formato de placa inválido")
    String placa,

    @Schema(description = "Valor máximo de desconto permitido para a venda", example = "5000.0")
    @Min(value = 0, message = "O desconto não pode ser negativo")
    double maximoDesconto,

    @Schema(description = "Se o carro está ou não vendido", example = "false")
    boolean vendido
) {
}
