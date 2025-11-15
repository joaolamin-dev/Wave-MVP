package br.com.fatec.wave.service;

import br.com.fatec.wave.dto.AtualizarUsuarioDTO;
import br.com.fatec.wave.dto.CriarUsuarioDTO;
import br.com.fatec.wave.dto.UsuarioResponseDTO;
import br.com.fatec.wave.exception.ResourceNotFoundException;
import br.com.fatec.wave.model.UserRole;
import br.com.fatec.wave.model.Usuario;
import br.com.fatec.wave.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));
    }

    protected UsuarioResponseDTO convertToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuarioById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado com o id: " + id));
    }

    @Override
    @Transactional
    public UsuarioResponseDTO criarUsuario(CriarUsuarioDTO criarUsuarioDTO) {
        if (repository.existsByEmail(criarUsuarioDTO.email())) {
            throw new DataIntegrityViolationException(
                    "Email já existente na plataforma: " + criarUsuarioDTO.email()
            );
        }

        Usuario usuario = new Usuario();
        usuario.setNome(criarUsuarioDTO.nome());
        usuario.setEmail(criarUsuarioDTO.email());
        usuario.setSenha(passwordEncoder.encode(criarUsuarioDTO.senha()));
        usuario.setRole(UserRole.USER);

        Usuario usuarioSalvo = repository.save(usuario);

        return convertToDTO(usuarioSalvo);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuarioExiste = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario não existe com o id: " + id));

        if (atualizarUsuarioDTO.nome() != null && !atualizarUsuarioDTO.nome().equals(usuarioExiste.getNome())) {
            usuarioExiste.setNome(atualizarUsuarioDTO.nome());
        }

        if (atualizarUsuarioDTO.email() != null && !atualizarUsuarioDTO.email().equals(usuarioExiste.getEmail())) {
            if (repository.existsByEmail(atualizarUsuarioDTO.email())) {
                throw new DataIntegrityViolationException(
                        "Email já existente na plataforma: " + atualizarUsuarioDTO.email()
                );
            }
            usuarioExiste.setEmail(atualizarUsuarioDTO.email());
        }

        if (atualizarUsuarioDTO.senha() != null && !atualizarUsuarioDTO.senha().isEmpty()) {
            usuarioExiste.setSenha(passwordEncoder.encode(atualizarUsuarioDTO.senha()));
        }

        Usuario usuarioAtualizado = repository.save(usuarioExiste);
        return convertToDTO(usuarioAtualizado);
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario não existe com o id: " + id);
        }
        repository.deleteById(id);
    }
}