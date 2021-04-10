package com.uestc.getthecourse.dao;

import com.uestc.getthecourse.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("select * from student where student.student_id = #{studentId}")
    Student getUserById(@Param("studentId") String studentId);

    @Update("update student set student.classes = #{classes} where student.student_id = #{sId}")
    int updateInfo(@Param("sId") String sId,@Param("classes") String classes);
}
