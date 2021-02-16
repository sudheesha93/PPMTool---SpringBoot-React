package com.example.ppmtool.controller;

import com.example.ppmtool.domain.Project;
import com.example.ppmtool.services.MapValidationErrorService;
import com.example.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService validationErrorService;

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorResponse = validationErrorService.mapValidationService(result);
        if(errorResponse != null){
            return errorResponse;
        }
        return  new ResponseEntity<Project>(projectService.saveOrUpdateProject(project), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectByID(@Valid @PathVariable(name = "projectId") String projectIdentifier){
        return  new ResponseEntity<Project>(projectService.fetchProjectByID(projectIdentifier), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects(){
        return  new ResponseEntity<List<Project>>(projectService.fetchAllProjects(), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectByID(@Valid @PathVariable(name = "projectId") String projectIdentifier){
        projectService.deleteProjectByID(projectIdentifier);
        return new ResponseEntity<String>("Project with Id:"+ projectIdentifier +" has been deleted", HttpStatus.OK);
    }
}
