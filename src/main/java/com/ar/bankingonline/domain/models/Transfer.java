package com.ar.bankingonline.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account origin;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account target;

    private Date date;
    private BigDecimal amount;
}
