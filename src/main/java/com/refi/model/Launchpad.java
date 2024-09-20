package com.refi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "launchpads")
public class Launchpad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(name = "creation_datetime", nullable = false)
    private Date creationDatetime;

    @Column(name = "expiration_datetime", nullable = false)
    private Date expirationDatetime;

    @Column(name = "token_emission", nullable = false)
    private Integer tokenEmission;

    @Column(name = "token_price", nullable = false)
    private Double tokenPrice;

    @Column(name = "ticker", unique = true, nullable = false)
    private String ticker;

    @Column(name = "contract", unique = true, nullable = false)
    private String contract;

    @Column(name = "min_buy", nullable = false)
    private Double minBuy;

    @Column(name = "max_buy", nullable = false)
    private Double maxBuy;

    @Column(name = "is_pool_reached", nullable = false)
    private Boolean isPoolReached;

    @ManyToOne
    @JoinColumn(name = "dao_llc_id", nullable = false)
    private DaoLlc daoLlc;
}
