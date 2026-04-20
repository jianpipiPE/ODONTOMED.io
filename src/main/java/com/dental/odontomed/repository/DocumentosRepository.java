package com.dental.odontomed.repository;
import java.util.List;
import com.dental.odontomed.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentosRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByUsuarioId(Long usuarioId);
}
