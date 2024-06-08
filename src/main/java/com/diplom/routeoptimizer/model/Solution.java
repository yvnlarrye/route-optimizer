package com.diplom.routeoptimizer.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "solutions")
@NoArgsConstructor
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solution_json", columnDefinition = "TEXT")
    private String solutionJson;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Solution(String solutionJson, User user) {
        this.solutionJson = solutionJson;
        this.user = user;
    }
}
