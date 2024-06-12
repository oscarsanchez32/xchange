package com.example.xchange.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    
    @Column(name = "short_desc", length = 400)
    private String shortDesc;



    @Column(name = "img_path")
    private String imgPath;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_detail_id")
    private EventDetail eventDetail;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<Comment> comment;

    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "userInventory")
    private List<ApplicationUser> ownedBy;

   

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdAt;

}