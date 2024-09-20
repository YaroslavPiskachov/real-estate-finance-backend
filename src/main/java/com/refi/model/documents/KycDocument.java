package com.refi.model.documents;

import com.refi.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "kyc_documents")
public class KycDocument extends Document {

    @Column(name = "verification_status")
    private String verificationStatus;

    @Column(name = "verification_date")
    private Instant verificationDate;
}
