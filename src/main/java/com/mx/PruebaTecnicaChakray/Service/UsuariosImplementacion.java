package com.mx.PruebaTecnicaChakray.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.mx.PruebaTecnicaChakray.Dao.UsuariosDao;
import com.mx.PruebaTecnicaChakray.Entity.Usuarios;
import com.mx.PruebaTecnicaChakray.Specification.UsuarioSpecification;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuariosImplementacion implements InterfaceUsuarios {

    @Autowired
    private UsuariosDao usuariosDao;

    @Override
    public List<Usuarios> obtenerTodos(Sort sort) {
        return usuariosDao.findAll(sort);
    }

    @Override
    public List<Usuarios> obtenerFiltrados(String atributo, String operador, String valor) {
        Specification<Usuarios> spec = UsuarioSpecification.filtrarPor(atributo, operador, valor);
        return usuariosDao.findAll(spec);
    }

    @Override
    public Usuarios guardar(Usuarios usuario) {
        validarLongitudes(usuario);
        
        if (usuario.getAddresses() != null) {
            usuario.getAddresses().forEach(address -> address.setUsuario(usuario));
        }
        
        if (usuario.getTaxId() == null || usuario.getTaxId().trim().isEmpty()) {
            throw new RuntimeException("El taxId es obligatorio");
        }

        if (usuariosDao.existsByTaxId(usuario.getTaxId())) {
            throw new RuntimeException("El taxId ya existe");
        }
        
        if (usuario.getAddresses() != null) {
            usuario.getAddresses().forEach(address -> address.setUsuario(usuario));
        }

        usuario.setId(UUID.randomUUID().toString());
        usuario.setCreatedAt(new Date());

        return usuariosDao.save(usuario);
    }

    @Override
    public Optional<Usuarios> actualizar(String id, Usuarios usuarioActualizado) {
        return usuariosDao.findById(id).map(usuarioExistente -> {

            validarLongitudes(usuarioActualizado);
            
            if (usuarioActualizado.getAddresses() != null) {
            	usuarioActualizado.getAddresses().forEach(address -> address.setUsuario(usuarioActualizado));
            }

            if (usuarioActualizado.getEmail() != null)
                usuarioExistente.setEmail(usuarioActualizado.getEmail());

            if (usuarioActualizado.getName() != null)
                usuarioExistente.setName(usuarioActualizado.getName());

            if (usuarioActualizado.getPhone() != null)
                usuarioExistente.setPhone(usuarioActualizado.getPhone());

            if (usuarioActualizado.getTaxId() != null) {
                if (!usuarioExistente.getTaxId().equals(usuarioActualizado.getTaxId()) &&
                        usuariosDao.existsByTaxId(usuarioActualizado.getTaxId())) {
                    throw new RuntimeException("El nuevo taxId ya existe");
                }
                usuarioExistente.setTaxId(usuarioActualizado.getTaxId());
            }

            if (usuarioActualizado.getPassword() != null)
                usuarioExistente.setPassword(usuarioActualizado.getPassword());
            
            

            return usuariosDao.save(usuarioExistente);
        });
    }

    @Override
    public boolean eliminar(String id) {
        if (usuariosDao.existsById(id)) {
            usuariosDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Usuarios> autenticar(String taxId, String password) {
        return usuariosDao.findByTaxId(taxId)
                .filter(usuario -> usuario.getPassword() != null &&
                        usuario.getPassword().trim().equals(password.trim()));
    }

    private void validarLongitudes(Usuarios usuario) {
        if (usuario.getTaxId() != null) {
            int len = usuario.getTaxId().length();
            if (len < 12 || len > 13) {
                throw new RuntimeException("taxId debe tener entre 12 y 13 caracteres");
            }
        }
        if (usuario.getPhone() != null && usuario.getPhone().length() > 13) {
            throw new RuntimeException("phone no puede tener más de 13 caracteres");
        }
    }
}
