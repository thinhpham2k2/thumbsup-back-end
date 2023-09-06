package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "City")
@Table(name = "tbl_city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Nationalized
    @Column(name = "city_name")
    private String cityName;

    @Nationalized
    @Column(name = "image", length = 8000)
    private String image;

    @Lob
    @Nationalized
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Customer> customerList;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Store> storeList;
}
