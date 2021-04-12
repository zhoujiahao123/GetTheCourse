package com.uestc.getthecourse.dao;

import com.uestc.getthecourse.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseDao {

    @Select("select * from course")
    List<Course> listAllCourse();
}
