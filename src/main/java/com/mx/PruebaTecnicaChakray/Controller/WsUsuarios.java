package com.mx.PruebaTecnicaChakray.Controller;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mx.PruebaTecnicaChakray.Entity.Usuarios;
import com.mx.PruebaTecnicaChakray.Service.InterfaceUsuarios;

@RestController
@RequestMapping("/usuariosApi")
public class WsUsuarios {
	
	@Autowired
    private InterfaceUsuarios usuariosService;
	
	 @GetMapping
	    public ResponseEntity<List<Usuarios>> obtenerUsuarios(
	            @RequestParam(required = false) String sortedBy,
	            @RequestParam(required = false) String filter) {

	        if (filter != null) {
	            // Parsear filter: atributo+operador+valor
	            String[] partes = filter.split("\\+");
	            if (partes.length == 3) {
	                String atributo = partes[0];
	                String operador = partes[1];
	                String valor = partes[2];
	                List<Usuarios> resultados = usuariosService.obtenerFiltrados(atributo, operador, valor);
	                return ResponseEntity.ok(resultados);
	            } else {
	                return ResponseEntity.badRequest().build();
	            }
	        } else if (sortedBy != null && !sortedBy.isEmpty()) {
	            Sort sort = Sort.by(mapearAtributo(sortedBy));
	            return ResponseEntity.ok(usuariosService.obtenerTodos(sort));
	        } else {
	            return ResponseEntity.ok(usuariosService.obtenerTodos(Sort.unsorted()));
	        }
	    }

	    // POST /users
	 @PostMapping
	 public ResponseEntity<Object> crearUsuario(@RequestBody Usuarios usuario) {
	     try {
	         Usuarios nuevo = usuariosService.guardar(usuario);
	         return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
	     } catch (RuntimeException e) {
	         e.printStackTrace();
	         return ResponseEntity.badRequest().body(e.getMessage());
	     }
	 }

	    // PATCH /users/{id}
	    @PatchMapping("/{id}")
	    public ResponseEntity<Usuarios> actualizarUsuario(@PathVariable String id, @RequestBody Usuarios usuario) {
	        Optional<Usuarios> actualizado = usuariosService.actualizar(id, usuario);
	        return actualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // DELETE /users/{id}
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
	        boolean eliminado = usuariosService.eliminar(id);
	        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	    }

	    // POST /login
	    @PostMapping("/login")
	    public ResponseEntity<Void> login(@RequestParam String taxId, @RequestParam String password) {	
	        Optional<Usuarios> usuario = usuariosService.autenticar(taxId, password);
	        return usuario.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    private String mapearAtributo(String sortedBy) {
	        switch (sortedBy) {
	            case "email": return "email";
	            case "id": return "id";
	            case "name": return "name";
	            case "phone": return "phone";
	            case "tax_id": return "taxId";
	            case "created_at": return "createdAt";
	            default: return "id";
	        }
	    }

}
