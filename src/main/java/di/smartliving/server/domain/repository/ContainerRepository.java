package di.smartliving.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.Container;

public interface ContainerRepository extends JpaRepository<Container, Container.ID> {
}