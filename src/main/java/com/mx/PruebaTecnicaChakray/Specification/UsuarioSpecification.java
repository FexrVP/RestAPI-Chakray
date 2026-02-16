package com.mx.PruebaTecnicaChakray.Specification;

import java.util.Date;
import org.springframework.data.jpa.domain.Specification;
import com.mx.PruebaTecnicaChakray.Entity.Usuarios;
import jakarta.persistence.criteria.*;

public class UsuarioSpecification {
	
	public static Specification<Usuarios> filtrarPor(String atributo, String operador, String valor) {
        return (root, query, criteriaBuilder) -> {
            Path<Object> path = obtenerPath(root, atributo);
            if (path == null) return criteriaBuilder.conjunction();

            switch (operador) {
                case "eq":
                    if (path.getJavaType() == String.class) {
                        return criteriaBuilder.equal(path, valor);
                    } else if (path.getJavaType() == Date.class) {
                        try {
                            Date fecha = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm").parse(valor);
                            return criteriaBuilder.equal(path, fecha);
                        } catch (Exception e) {
                            return criteriaBuilder.conjunction();
                        }
                    }
                    break;
                case "co":
                    return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + valor.toLowerCase() + "%");
                case "sw":
                    return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), valor.toLowerCase() + "%");
                case "ew":
                    return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + valor.toLowerCase());
                default:
                    return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.conjunction();
        };
    }

    private static Path<Object> obtenerPath(Root<Usuarios> root, String atributo) {
        switch (atributo) {
            case "id": return root.get("id");
            case "email": return root.get("email");
            case "name": return root.get("name");
            case "phone": return root.get("phone");
            case "tax_id": return root.get("taxId");
            case "created_at": return root.get("createAt");
            default: return null;
        }
    }
}
