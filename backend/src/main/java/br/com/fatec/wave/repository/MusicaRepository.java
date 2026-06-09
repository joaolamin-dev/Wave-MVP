package br.com.fatec.wave.repository;

import br.com.fatec.wave.model.Musica;
import br.com.fatec.wave.model.GeneroMusical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
    // Busca parametrizada
    Page<Musica> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Musica> findByGenero(GeneroMusical genero, Pageable pageable);
}