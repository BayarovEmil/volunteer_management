package com.cognito.volunteer_managment_system.entity;

import com.cognito.volunteer_managment_system.core.common.BaseEntity;
import com.cognito.volunteer_managment_system.core.security.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "team")
public class Team extends BaseEntity {

    @Column(unique = true)
    private String teamName;
    private String teamLeader;
    private String memberName;
    private String secondLeader;
    private Integer memberCount;
    private String bio;
    private Double rating;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "team")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "assignedTeam")
    private List<Task> tasks;

    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        double roundedRate = Math.round(rate * 10.0) / 10.0;

        // Return 4.0 if roundedRate is less than 4.5, otherwise return 4.5
        return roundedRate;
    }
}
