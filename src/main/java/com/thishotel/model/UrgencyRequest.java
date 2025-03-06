package com.thishotel.model;

import com.thishotel.enums.UrgencyPriority;
import com.thishotel.enums.UrgencyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "urgency_request")
@Getter
@Setter
@NoArgsConstructor
public class UrgencyRequest {


//    VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyStatus status = UrgencyStatus.PENDING;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "reported_by", nullable = false)
    private Receptionist reportedBy;

    @ManyToOne
    @JoinColumn(name = "handled_by")
    private Cleaner handledBy;

    private String resolutionNotes;

}
