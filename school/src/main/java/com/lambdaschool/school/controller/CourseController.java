package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;


    @ApiOperation(value = "Returns all courses", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieving all courses", responseContainer = "List", response =
                    Course.class)})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        ArrayList<Course> myCourses = courseService.findAll();
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }


    @ApiOperation(value = "Returns the amount of students are in each course", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Students Counted", responseContainer = "List", response = Course.class)})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }


    @ApiOperation(value = "Retrieves a course by the course's ID", response = Course.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course Found", responseContainer = "List", response = Course.class),
            @ApiResponse(code = 404, message = "Course Not Found", response = ErrorDetail.class)})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
