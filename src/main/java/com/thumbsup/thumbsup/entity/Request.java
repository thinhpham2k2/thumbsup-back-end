package com.thumbsup.thumbsup.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Request")
@Table(name = "tbl_request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "method")
    @Nationalized
    private String method;

    @Column(name = "account_number")
    @Nationalized
    private String accountNumber;

    @Column(name = "bank_name")
    @Nationalized
    private String bankName;

    @Column(
            name = "note",
            length = 8000)
    @Nationalized
    private String note;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_accept")
    private LocalDateTime dateAccept;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "store_id")
    private Store store;
}
