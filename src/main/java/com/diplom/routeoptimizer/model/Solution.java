package com.diplom.routeoptimizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "solutions")
@NoArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "solution_json", columnDefinition = "TEXT")
    private String solutionJson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
