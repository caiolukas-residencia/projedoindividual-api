package org.serratec.projetoindividual.repository;

import org.serratec.projetoindividual.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

    // Pesquisa por placa
    Optional<Veiculo> findByPlaca(String placa);

    // Pesquisa por marca
    List<Veiculo> findByMarcaContainingIgnoreCase(String marca);

    // Pesquisa por modelo
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    // Verifica se a placa já existe
    boolean existsByPlaca (String placa);
}
