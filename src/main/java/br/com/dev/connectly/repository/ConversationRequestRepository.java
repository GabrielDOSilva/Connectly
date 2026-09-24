package br.com.dev.connectly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dev.connectly.entity.ConversationRequest;
import br.com.dev.connectly.entity.ConversationRequestStatus;
import br.com.dev.connectly.entity.Users;

public interface ConversationRequestRepository  extends JpaRepository<ConversationRequest, Long>{

	@Query("""
		    SELECT COUNT(r) > 0
		    FROM ConversationRequest r
		    WHERE r.status = :status
		      AND (
		          (r.sender = :user1 AND r.receiver = :user2)
		          OR
		          (r.sender = :user2 AND r.receiver = :user1)
		      )
		""")
	boolean existsBetweenUsers(
			
			
			 @Param("user1") 
			 Users user1,
			 
		     @Param("user2") 
			 Users user2,
			 
		     @Param("status") 
			 ConversationRequestStatus status
	);
	
	List<ConversationRequest> findByReceiverAndStatus(
	        Users receiver,
	        ConversationRequestStatus status
	);
	
}
