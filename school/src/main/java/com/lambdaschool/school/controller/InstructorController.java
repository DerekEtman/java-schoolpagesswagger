package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/Instructor")
public class InstructorController {
    @Autowired InstructorService instructorService;

    @ApiOperation(value = "Creates a new Student.", notes = "The newly created Student id will be sent in the " +
            "location header.", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Student Created Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error creating Student", response = ErrorDetail.class)
    } )
    @PostMapping(value = "/Instructor",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Instructor newInstructor) throws URISyntaxException
    {
        newInstructor = instructorService.save(newInstructor);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newInstructorURI =
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{Instructid}").buildAndExpand(newInstructor.getInstructid()).toUri();
        responseHeaders.setLocation(newInstructorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/Instructor/{Instructid}")
    public ResponseEntity<?> updateInstructor(
            @RequestBody
                    Instructor updateInstructor,
            @PathVariable
                    long Instructid)
    {
        instructorService.update(updateInstructor, Instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/Instructor/{Instructid}")
    public ResponseEntity<?> deleteInstructorById(
            @PathVariable
                    long Instructid)
    {
        instructorService.delete(Instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
