package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.AtualizarUsuarioDTO;
import br.com.fatec.wave.dto.CriarUsuarioDTO;
import br.com.fatec.wave.dto.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Page<UsuarioResponseDTO> getAllUsuarios(Pageable pageable);

    Page<UsuarioResponseDTO> buscarPorNome(String nome, Pageable pageable);

    UsuarioResponseDTO getUsuarioById(Long id);

    UsuarioResponseDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO);

    UsuarioResponseDTO atualizarUsuario(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO);

    void deletarUsuario(Long id);
}