package org.serratec.projetoindividual.repository;

import org.serratec.projetoindividual.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    // Pesquisa por CPF
    Optional<Cliente> findByCpf(String cpf);

    // Pesquisa por nome
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Verifica se o CPF já existe
    boolean existsByCpf(String cpf);
}
