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

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolutionService {
    private final SolutionRepository repository;
    private final UserService userService;

    public VrpSolution readSolutionFromDTO(SolutionDTO solution) {
        try {
            return new ObjectMapper().
                    readValue(solution.getSolutionJson(), VrpSolution.class);
        } catch (JsonProcessingException e) {
            throw new IncorrectSolutionFormatException("Incorrect json format of solution", e);
        }
    }

    public Solution create(SolutionDTO solutionDTO) {

        readSolutionFromDTO(solutionDTO);

        Solution solution = new Solution();
        solution.setSolutionJson(solutionDTO.getSolutionJson());
        solution.setCreatedAt(new Date());
        solution.setName(solutionDTO.getName());
        solution.setUser(userService.getCurrentUser());

        return create(solution);

    }

    private Solution create(Solution solution) {
        return repository.save(solution);
    }

    public void removeBYId(Long id) {
        repository.deleteById(id);
    }

    public List<Solution> getUserSolutions(Long userId) {
        User currentUser = userService.getById(userId);
        return repository.findAllByUser(currentUser);
    }
}
