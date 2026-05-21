package org.serratec.projetoindividual.service;

import org.serratec.projetoindividual.entity.Cliente;
import org.serratec.projetoindividual.exception.DadoCadastradoException;
import org.serratec.projetoindividual.exception.DadoNaoEncontradoException;
import org.serratec.projetoindividual.model.ClienteCriar;
import org.serratec.projetoindividual.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente inserir(ClienteCriar clienteCriar) {
        if(clienteRepository.existsByCpf(clienteCriar.cpf())) {
            throw new DadoCadastradoException("409 - CPF já cadastrado no sistema.");
        }

        Cliente novoCliente = new Cliente(clienteCriar.nome(), clienteCriar.telefone(), clienteCriar.cpf(), clienteCriar.email());

        return clienteRepository.save(novoCliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorCpf(String cpf) {
        Optional<Cliente> resultado = clienteRepository.findByCpf(cpf);

        if(resultado.isEmpty()) {
            throw new DadoNaoEncontradoException("404 - Cliente não encontrado");
        }

        return resultado.get();
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public void deletar (UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new DadoNaoEncontradoException("404 - Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
