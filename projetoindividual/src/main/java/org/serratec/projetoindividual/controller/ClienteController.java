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
import org.serratec.projetoindividual.exception.ErroResposta;
import org.serratec.projetoindividual.model.ClienteCriar;
import org.serratec.projetoindividual.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Cliente", description = "Operações para gerenciamento de clientes")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cadastrar novo cliente",
               description = "Insere um novo cliente no sistema. O CPF deve ser único.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Cliente.class))
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
    public ResponseEntity<Cliente> cadastrarCliente(@Valid @RequestBody ClienteCriar cliente) {
        Cliente clienteSalvo = clienteService.inserir(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @Operation(summary = "Listar cliente ou buscar por nome/cpf",
            description = "Retorna todos os clientes. Caso informe os parâmetros, passa pela filtragem.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de clientes retornada",
                    content = @Content(schema = @Schema(implementation = Cliente.class))
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
    public ResponseEntity<List<Cliente>> listarCliente(
            @Parameter(description = "Filtrar por parte do nome do cliente")
            @RequestParam(required = false) String nome,

            @Parameter(description = "Filtrar por CPF do cliente")
            @RequestParam(required = false) String cpf) {

        List<Cliente> resultado = new ArrayList<>();

        if (cpf != null && !cpf.isBlank()) {
            resultado.add(clienteService.buscarPorCpf(cpf));
        } else if (nome != null && !nome.isBlank()) {
            resultado.addAll(clienteService.buscarPorNome(nome));
        } else {
            resultado.addAll(clienteService.listarTodos());
        }

        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Remover um cliente",
            description = "Remove permanentemente um cliente do sistema através do ID.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente removido com sucesso"
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
    public ResponseEntity<Void> removerCliente(
            @Parameter(description = "ID único do cliente (UUID)", required = true)
            @PathVariable UUID id
    ) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
