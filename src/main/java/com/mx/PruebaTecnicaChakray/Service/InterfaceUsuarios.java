package com.mx.PruebaTecnicaChakray.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.mx.PruebaTecnicaChakray.Entity.Usuarios;

public interface InterfaceUsuarios {
	
    List<Usuarios> obtenerTodos(Sort sort);
    List<Usuarios> obtenerFiltrados(String atributo, String operador, String valor);
    Usuarios guardar(Usuarios usuario);
    Optional<Usuarios> actualizar(String id, Usuarios usuarioActualizado);
    boolean eliminar(String id);
    Optional<Usuarios> autenticar(String taxId, String password);
}
