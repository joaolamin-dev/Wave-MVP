package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.AtualizarUsuarioDTO;
import br.com.fatec.wave.dto.CriarUsuarioDTO;
import br.com.fatec.wave.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> getAllUsuarios();

    UsuarioResponseDTO getUsuarioById(Long id);

    UsuarioResponseDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO);

    UsuarioResponseDTO atualizarUsuario(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO);

    void deletarUsuario(Long id);
}
