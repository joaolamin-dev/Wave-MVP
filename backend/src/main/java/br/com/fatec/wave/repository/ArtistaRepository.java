package br.com.fatec.wave.repository;

import br.com.fatec.wave.model.Artista;
import br.com.fatec.wave.model.TipoArtista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    // Busca parametrizada
    Page<Artista> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Artista> findByTipo(TipoArtista tipo, Pageable pageable);
}