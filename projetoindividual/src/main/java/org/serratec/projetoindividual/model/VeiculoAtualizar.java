package org.serratec.projetoindividual.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record VeiculoAtualizar(
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
    float valor,

    @Schema(description = "Valor máximo de desconto permitido para a venda", example = "5000.0")
    @Min(value = 0, message = "O desconto não pode ser negativo")
    float maximoDesconto,

    @Schema(description = "Se o carro está ou não vendido", example = "false")
    boolean vendido,

    @Schema(description = "Valor pelo qual o veículo foi vendido (Obrigatório se vendido = true)", example = "190000.0")
    Float valorVenda
) {
    @AssertTrue(message = "O valor da venda é obrigatório quando o veículo está marcado como vendido")
    public boolean isValorVendaValido() {
        if (Boolean.TRUE.equals(vendido) && valorVenda == null) {
            return false;
        }
        return true;
    }
}
