package com.uestc.getthecourse.service;

import com.uestc.getthecourse.dao.CourseDao;
import com.uestc.getthecourse.entity.Course;
import com.uestc.getthecourse.exception.GlobalException;
import com.uestc.getthecourse.result.CodeMsg;
import com.uestc.getthecourse.result.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {

    @Resource
    CourseDao courseDao;

    public Result<List<Course>> listAllCourse() {
        List<Course> courses = courseDao.listAllCourse();
        if (courses == null || courses.size() == 0) throw new GlobalException(CodeMsg.EMPTY_COURSES);
        return Result.success(courses);
    }
}
