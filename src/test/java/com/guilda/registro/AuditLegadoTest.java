package com.guilda.registro;

import com.guilda.registro.domain.legacy.Usuario;
import com.guilda.registro.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AuditLegadoTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    public void deveCarregarUsuariosERelacionamentosLegados() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        assertNotNull(usuarios);
        System.out.println("Conexão com audit bem sucedida.");
        System.out.println("Total de usuários encontrados: " + usuarios.size());

        if (!usuarios.isEmpty()) {
            Usuario user = usuarios.get(0);
            System.out.println("Usuário Exemplo: " + user.getNome());
            System.out.println("Organização vinculada: " + user.getOrganizacao().getNome());
            System.out.println("Quantidade de Roles (Papéis): " + user.getRoles().size());
        }
    }
}