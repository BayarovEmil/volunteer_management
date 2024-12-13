package com.cognito.volunteer_managment_system.dataAccess;

import com.cognito.volunteer_managment_system.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

    @Query("""
            select feedback
            from Feedback feedback
            where feedback.team.id=:teamId
            """)
    Page<Feedback> findAllFeedbacksByTeamId(Integer teamId, Pageable pageable);
}
