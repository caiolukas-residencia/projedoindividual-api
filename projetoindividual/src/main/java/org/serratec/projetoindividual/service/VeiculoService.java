package org.serratec.projetoindividual.service;

import org.serratec.projetoindividual.entity.Cliente;
import org.serratec.projetoindividual.entity.Veiculo;
import org.serratec.projetoindividual.exception.DadoCadastradoException;
import org.serratec.projetoindividual.exception.DadoNaoEncontradoException;
import org.serratec.projetoindividual.model.VeiculoAtualizar;
import org.serratec.projetoindividual.model.VeiculoCriar;
import org.serratec.projetoindividual.repository.ClienteRepository;
import org.serratec.projetoindividual.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Veiculo inserir(VeiculoCriar veiculoCriar) {
        if(veiculoRepository.existsByPlaca(veiculoCriar.placa())) {
            throw new DadoCadastradoException("409 - Veículo já cadastrado");
        }

        Cliente donoVeiculo = clienteRepository.findById(veiculoCriar.clienteId())
                .orElseThrow(() -> new DadoNaoEncontradoException("404 - Cliente dono do veículo não encontrado"));

        Veiculo novoVeiculo = new Veiculo(donoVeiculo, veiculoCriar.marca(), veiculoCriar.modelo(), veiculoCriar.ano(), veiculoCriar.valor(), veiculoCriar.placa(), veiculoCriar.maximoDesconto(), false, null);

        return veiculoRepository.save(novoVeiculo);
    }

    public Veiculo atualizar(UUID id, VeiculoAtualizar dto) {
        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() -> new DadoNaoEncontradoException("404 - Veículo não encontrado"));

        veiculoExistente.setMarca(dto.marca());
        veiculoExistente.setModelo(dto.modelo());
        veiculoExistente.setAno(dto.ano());
        veiculoExistente.setValor(dto.valor());
        veiculoExistente.setMaximoDesconto(dto.maximoDesconto());
        veiculoExistente.setVendido(dto.vendido());
        veiculoExistente.setValorVenda(dto.valorVenda());

        return veiculoRepository.save(veiculoExistente);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new DadoNaoEncontradoException("404 - Veículo não encontrado"));
    }

    public List<Veiculo> buscarPorMarca(String marca) {
        return veiculoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public List<Veiculo> buscarPorModelo(String modelo) {
        return veiculoRepository.findByModeloContainingIgnoreCase(modelo);
    }

    public void deletar (UUID id) {
        if(!veiculoRepository.existsById(id)) {
            throw new DadoNaoEncontradoException("404 - Veículo não encontrado");
        }

        veiculoRepository.deleteById(id);
    }
}
