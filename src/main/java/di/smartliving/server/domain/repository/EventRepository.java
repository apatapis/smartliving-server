package di.smartliving.server.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.EventType;

public interface EventRepository extends JpaRepository<Event, Long> {

	public Optional<Event> findTop1ByContainerIdAndTypeOrderByCreatedDateDesc(Container.ID containerId, EventType type);

	public Page<Event> findByContainerIdAndType(Container.ID containerId, EventType type, Pageable pageable);

	public List<Event> findByContainerIdAndType(Container.ID containerId, EventType type);

}