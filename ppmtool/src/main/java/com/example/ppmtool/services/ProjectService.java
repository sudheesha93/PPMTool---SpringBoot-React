package com.example.ppmtool.services;

import com.example.ppmtool.domain.Project;
import com.example.ppmtool.exceptions.ProjectIdException;
import com.example.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Create a Project
    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID:  " + project.getProjectIdentifier().toUpperCase() + " already exists");
        }
    }

    // Fetch a project by ID
    public Project fetchProjectByID(String projectIdentifier) {

        Project project = new Project();
        project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID not found");
        }
        return project;

    }

    // Fetch all the projects
    public List<Project> fetchAllProjects() {

        List<Project> projects = new ArrayList<Project>();
        projects = (List<Project>) projectRepository.findAll();
        if (projects.isEmpty() || projects == null) {
            throw new ProjectIdException("NO Projects Found");
        }
        return projects;
    }

    // Delete a project based on ID
    public void deleteProjectByID(String projectIdentifier) {

        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID not found - Unable to delete");
        }

        projectRepository.delete(project);
    }

}