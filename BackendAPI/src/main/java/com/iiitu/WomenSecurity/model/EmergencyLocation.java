package com.iiitu.WomenSecurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "emergency_locations")
public class EmergencyLocation {

    @Id
    private String id;

    private String userId;
    private String status;
    @Field(write = Field.Write.ALWAYS)
    private String priority;
    private double latitude;
    private double longitude;
    private Coordinates coordinates;
    private String location;
    private String description;
    private String voiceTranscript;
    private String voiceUrl;
    private String reporterName;
    private String reporterPhone;
    private String assignedStationId;
    private List<String> linkedStationIds = new ArrayList<>();
    private List<String> evidence = new ArrayList<>();
    private String note;
    private String type;
    private String phoneNumber;
    private String email;

    private LocalDateTime time;

    public EmergencyLocation(){}

    public EmergencyLocation(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = LocalDateTime.now();
    }

    public EmergencyLocation(
            String userId,
            String status,
            double latitude,
            double longitude,
            String note,
            String type,
            String phoneNumber,
            String email
    ) {
        this.userId = userId;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.time = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getUserId() { return userId; }

    public String getStatus() { return status; }

    public String getPriority() { return priority; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public Coordinates getCoordinates() { return coordinates; }

    public String getLocation() { return location; }

    public String getDescription() { return description; }

    public String getVoiceTranscript() { return voiceTranscript; }

    public String getVoiceUrl() { return voiceUrl; }

    public String getReporterName() { return reporterName; }

    public String getReporterPhone() { return reporterPhone; }

    public String getAssignedStationId() { return assignedStationId; }

    public List<String> getLinkedStationIds() { return linkedStationIds; }

    public List<String> getEvidence() { return evidence; }

    public String getNote() { return note; }

    public String getType() { return type; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getEmail() { return email; }

    public LocalDateTime getTime() { return time; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setStatus(String status) { this.status = status; }

    public void setPriority(String priority) { this.priority = priority; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        if (coordinates != null) {
            this.latitude = coordinates.getLat();
            this.longitude = coordinates.getLng();
        }
    }

    public void setLocation(String location) { this.location = location; }

    public void setDescription(String description) { this.description = description; }

    public void setVoiceTranscript(String voiceTranscript) { this.voiceTranscript = voiceTranscript; }

    public void setVoiceUrl(String voiceUrl) { this.voiceUrl = voiceUrl; }

    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }

    public void setAssignedStationId(String assignedStationId) { this.assignedStationId = assignedStationId; }

    public void setLinkedStationIds(List<String> linkedStationIds) {
        this.linkedStationIds = linkedStationIds != null ? linkedStationIds : new ArrayList<>();
    }

    public void setEvidence(List<String> evidence) {
        this.evidence = evidence != null ? evidence : new ArrayList<>();
    }

    public void setNote(String note) { this.note = note; }

    public void setType(String type) { this.type = type; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setEmail(String email) { this.email = email; }

    public void setTime(LocalDateTime time) { this.time = time; }
}
