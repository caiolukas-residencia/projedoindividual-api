package org.serratec.projetoindividual.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.serratec.projetoindividual.entity.Cliente;
import org.serratec.projetoindividual.entity.Veiculo;
import org.serratec.projetoindividual.exception.ErroResposta;
import org.serratec.projetoindividual.model.VeiculoAtualizar;
import org.serratec.projetoindividual.model.VeiculoCriar;
import org.serratec.projetoindividual.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Veiculo", description = "Operações para gerenciamento de veículos")
@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @Operation(summary = "Cadastrar novo veículo",
            description = "Insere um novo veículo no sistema. A placa deve ser único.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Veiculo.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Dados já cadstrados",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            )
    })
    @PostMapping
    public ResponseEntity<Veiculo> cadastrarVeiculo(@Valid @RequestBody VeiculoCriar veiculo) {
        Veiculo veiculoSalvo = veiculoService.inserir(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoSalvo);
    }

    @Operation(summary = "Listar veículo ou buscar por placa/marca/modelo",
            description = "Retorna todos os veículos. Caso informe os parâmetros, passa pela filtragem.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos retornada",
                    content = @Content(schema = @Schema(implementation = Veiculo.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dados não encontrados",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<Veiculo>> listarVeiculos(
            @Parameter(description = "Filtrar por placa")
            @RequestParam(required = false) String placa,

            @Parameter(description = "Filtrar por modelo")
            @RequestParam(required = false) String modelo,

            @Parameter(description = "Filtrar por marca")
            @RequestParam(required = false) String marca
    ) {

        List<Veiculo> resultado = new ArrayList<>();

        if (placa != null && !placa.isBlank()) {
            resultado.add(veiculoService.buscarPorPlaca(placa));
        } else if (modelo != null && !modelo.isBlank()) {
            resultado.addAll(veiculoService.buscarPorModelo(modelo));
        } else if (marca != null && !marca.isBlank()) {
            resultado.addAll(veiculoService.buscarPorMarca(marca));
        } else {
            resultado.addAll(veiculoService.listarTodos());
        }

        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Atualizar dados de um veículo",
            description = "Atualiza as informações de um veículo existente pelo ID. Caso seja marcado como vendido, o valor da venda torna-se obrigatório.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = Veiculo.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizarVeiculo(
            @Parameter(description = "ID único do veículo", required = true)
            @PathVariable UUID id,

            @Valid @RequestBody VeiculoAtualizar veiculo
    ) {
        Veiculo veiculoAtualizado = veiculoService.atualizar(id, veiculo);
        return ResponseEntity.ok(veiculoAtualizado);
    }

    @Operation(summary = "Remover um veículo",
            description = "Remove permanentemente um veículo do sistema através do ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Veículo removido com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Dados não encontrados",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErroResposta.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerVeiculo(
            @Parameter(description = "ID único do veículo (UUID)", required = true)
            @PathVariable UUID id
    ) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
