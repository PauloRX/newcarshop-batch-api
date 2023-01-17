package br.com.paulorx.newcarshopbatchapi.repository;

import br.com.paulorx.newcarshopbatchapi.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
}
