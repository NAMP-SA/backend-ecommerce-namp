package com.namp.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Review")
public class Review implements Serializable {
    @Id
    @Column(name = "idReview")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long idReview;

    @NotEmpty(message = "El titulo no debe estar vacio")
    private String subject;

    @NotEmpty(message = "El mensaje no debe estar vacio")
    @Column(columnDefinition = "TEXT")
    private String message;

    @NotNull(message = "El usuario no puede estar vacio")
    @ManyToOne
    @JoinColumn(name = "fk_user", referencedColumnName = "idUser")
    private User idUser;
}
