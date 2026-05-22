package org.serratec.projetoindividual.exception;

@io.swagger.v3.oas.annotations.media.Schema(hidden = true)
public record ErroResposta(String mensagem) {
    // Classe para lançar as mensagens de forma limpa
}
