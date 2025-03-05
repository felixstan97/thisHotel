package com.thishotel.model;

import com.thishotel.enums.UrgencyPriority;
import com.thishotel.enums.UrgencyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "urgency_request")
public class UrgencyRequest {


//    VARIABLES

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


//    GETTER SETTER

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UrgencyPriority getPriority() {
        return priority;
    }

    public void setPriority(UrgencyPriority priority) {
        this.priority = priority;
    }

    public UrgencyStatus getStatus() {
        return status;
    }

    public void setStatus(UrgencyStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public Receptionist getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Receptionist reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Cleaner getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(Cleaner handledBy) {
        this.handledBy = handledBy;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void setResolutionNotes(String resolutionNotes) {
        this.resolutionNotes = resolutionNotes;
    }


}
