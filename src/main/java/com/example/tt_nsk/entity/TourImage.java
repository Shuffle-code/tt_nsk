package com.example.tt_nsk.entity;

import com.example.tt_nsk.entity.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tour_image")
@Setter
@Getter
@NoArgsConstructor
public class TourImage extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "path")
    private String path;

    @Builder
    public TourImage(Long id, Tour tour, String path) {
        super(id);
        this.tour = tour;
        this.path = path;
    }
}
