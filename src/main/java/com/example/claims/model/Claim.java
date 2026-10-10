package com.example.claims.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Claimant name is mandatory")
    @Column(nullable = false)
    private String claimant;

    @NotBlank(message = "Policy number is mandatory")
    @Column(nullable = false)
    private String policyNumber;

    @NotBlank(message = "Policy issuer is mandatory")
    @Column(nullable = false)
    private String policyIssuer;

    @NotNull(message = "Age of claimant is mandatory")
    @Min(value = 0, message = "Age must be valid")
    @Column(nullable = false)
    private Integer ageOfClaimant;

    @NotBlank(message = "Claim reason is mandatory")
    @Column(nullable = false, length = 500)
    private String claimReason;

    @NotBlank(message = "Status is mandatory")
    @Column(nullable = false)
    private String status; // e.g., SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED

    @Column
    private String fileName; // Stored file name for document/evidence

    public Claim() {
    }

    public Claim(String claimant, String policyNumber, String policyIssuer, Integer ageOfClaimant, String claimReason, String status, String fileName) {
        this.claimant = claimant;
        this.policyNumber = policyNumber;
        this.policyIssuer = policyIssuer;
        this.ageOfClaimant = ageOfClaimant;
        this.claimReason = claimReason;
        this.status = status;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyIssuer() {
        return policyIssuer;
    }

    public void setPolicyIssuer(String policyIssuer) {
        this.policyIssuer = policyIssuer;
    }

    public Integer getAgeOfClaimant() {
        return ageOfClaimant;
    }

    public void setAgeOfClaimant(Integer ageOfClaimant) {
        this.ageOfClaimant = ageOfClaimant;
    }

    public String getClaimReason() {
        return claimReason;
    }

    public void setClaimReason(String claimReason) {
        this.claimReason = claimReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
