package com.uestc.getthecourse.dao;

import com.uestc.getthecourse.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from student where student.student_id = #{studentId}")
    Student getUserById(@Param("studentId") String studentId);
}
