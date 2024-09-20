package com.refi.model.documents;

import com.refi.model.DaoLlc;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "llc_documents")
public class LlcDocument extends Document {

}
