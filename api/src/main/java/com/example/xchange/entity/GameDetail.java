package com.example.xchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties("hibernateLazyInitializer")
@Table(name = "game_detail")
public class GameDetail {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "long_desc")
    @Size(max = 2048)
    private String longDesc;

    @NotBlank(message = "EAN no puede estar vacío")
    @Column(name = "ean")
    private String ean;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "language")
    private String language;
    
}
