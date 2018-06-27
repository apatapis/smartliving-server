package di.smartliving.server.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	public Optional<Message> findFirstByProfileIdAndTopicOrderByCreatedDateDesc(Long profileId, String topic);
	public Optional<Message> findTop10ByProfileIdAndTopicOrderByCreatedDateDesc(Long profileId, String topic);
}