package com.uestc.getthecourse.controller;

import com.uestc.getthecourse.config.UserInfo;
import com.uestc.getthecourse.entity.Course;
import com.uestc.getthecourse.entity.Student;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.result.Result;
import com.uestc.getthecourse.service.CourseService;
import org.springframework.web.bind.annotation.*;

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
     * @return 返回所有的课程
     */
    @GetMapping("/list_all")
    public Result<List<Course>> listAllCourse() {
        return courseService.listAllCourse();
    }

}
