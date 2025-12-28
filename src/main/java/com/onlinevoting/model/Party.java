
package com.onlinevoting.model;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "party")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Party extends AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Party name is mandatory")
    @Column(name = "name", nullable = false, length = 255)
    @UniqueElements
    private String name;

    @NotBlank(message = "Logo text is mandatory")
    @Column(name = "logo_text", nullable = false, columnDefinition = "TEXT")
    private String logoText;

    @NotBlank(message = "Party president is mandatory")
    @Column(name = "president_name", nullable = false, length = 255)
    private String presidentName;
}