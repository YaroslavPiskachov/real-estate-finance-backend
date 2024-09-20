package com.refi.model.documents;

import com.refi.model.Property;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "property_documents")
public class PropertyDocument extends Document {

}
