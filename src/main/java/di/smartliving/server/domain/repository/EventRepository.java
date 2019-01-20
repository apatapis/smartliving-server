package di.smartliving.server.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.EventType;

public interface EventRepository extends JpaRepository<Event, Long> {

	public Optional<Event> findTop1ByContainerIdAndTypeOrderByTimestampDesc(Container.ID containerId, EventType type);

}