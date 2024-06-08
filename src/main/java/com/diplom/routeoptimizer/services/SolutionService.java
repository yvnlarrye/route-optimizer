package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.vrp.SolutionDTO;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.IncorrectSolutionFormatException;
import com.diplom.routeoptimizer.model.Solution;
import com.diplom.routeoptimizer.model.User;
import com.diplom.routeoptimizer.repository.SolutionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository repository;
    private final UserService userService;

    public Solution create(SolutionDTO solution) {
        try {
            System.out.println(solution.getSolutionJson());
            new ObjectMapper().readValue(solution.getSolutionJson(), VrpSolution.class);
            User currentUser = userService.getCurrentUser();
            return create(new Solution(solution.getSolutionJson(), currentUser));
        } catch (JsonProcessingException e) {
            throw new IncorrectSolutionFormatException("Incorrect json format of solution", e);
        }
    }

    private Solution create(Solution solution) {
        return repository.save(solution);
    }

    public void removeBYId(Long id) {
        repository.deleteById(id);
    }

}
