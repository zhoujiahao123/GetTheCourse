package com.uestc.getthecourse.controller;

import com.uestc.getthecourse.entity.Course;
import com.uestc.getthecourse.result.Result;
import com.uestc.getthecourse.service.CourseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程相关的controller
 * 主要提供查询课程等操作
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    CourseService courseService;

    /**
     *
     * @return
     */
    @PostMapping("/list_all")
    public Result<List<Course>> listAllCourse() {
        return courseService.listAllCourse();
    }
}
