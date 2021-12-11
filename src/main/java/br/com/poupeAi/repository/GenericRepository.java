package br.com.poupeAi.repository;

import br.com.poupeAi.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.ativo=false where e.id = ?1")
    void deleteById(@NonNull Long id);

    @NonNull
    @Query(value = "SELECT e from #{#entityName} e where e.id = ?1 and e.ativo=true")
    Optional<T> findById(@NonNull Long id);

    List<T> findAllByAtivo(boolean ativo);
}
