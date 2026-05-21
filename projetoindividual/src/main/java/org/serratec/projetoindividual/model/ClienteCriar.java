package org.serratec.projetoindividual.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteCriar(
   @Schema(description = "Nome completo do cliente", example = "Caio Lukas Monteiro")
   @NotBlank(message = "O nome é obrigatório")
   String nome,

   @Schema(description = "Telefone de contato do cliente com DDD", example = "22999999999")
   @NotBlank(message = "O telefone é obrigatório")
   String telefone,

   @Schema(description = "O CPF do cliente, apenas números", example = "12345678910")
   @NotBlank(message = "O CPF é obrigatório")
   @CPF(message = "CPF inválido")
   String cpf,

   @Schema(description = "Endereço de e-mail válido do cliente", example = "caio.lukas@email.com")
   @NotBlank(message = "O e-mail é obrigatório")
   @Email(message = "E-mail inválido")
   String email
) {
}
