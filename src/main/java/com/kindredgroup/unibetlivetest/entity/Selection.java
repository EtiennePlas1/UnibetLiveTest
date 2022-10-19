package com.kindredgroup.unibetlivetest.entity;

import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "selection")
@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Selection {

    public Selection() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "current_odd")
    BigDecimal currentOdd;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    SelectionState state;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    SelectionResult result;

    @ManyToOne
    @JoinColumn(name = "market_id")
    Market market;    
    
}
