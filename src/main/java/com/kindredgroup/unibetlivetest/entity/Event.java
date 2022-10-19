package com.kindredgroup.unibetlivetest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Event")
@Entity
@Data
@Accessors(chain = true)
public class Event {


    public Event() {}

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "start_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @JsonIgnore
    @OneToMany(targetEntity=Market.class, mappedBy="event", fetch = FetchType.LAZY)
    private List<Market> markets = new ArrayList<>();
    
}
