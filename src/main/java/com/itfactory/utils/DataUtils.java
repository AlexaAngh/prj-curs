package com.itfactory.utils;

import com.itfactory.model.Course;
import com.itfactory.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataUtils {

    public Integer getnextCourseId(List<Course> cursuri){
        int maxId =0;

        for (Course curs : cursuri){
            int cursId = curs.getCourseID();
            maxId = Integer.max(cursId,maxId);

        }

        return maxId +1;

    }

    public Integer getnextStudentId(List<Student> studenti){
        int maxId =0;

        for (Student student : studenti){
            int studentId = student.getStudentId();
            maxId = Integer.max(studentId,maxId);

        }

        return maxId +1;

    }

    public Boolean isValidCourseId(int id,List<Course> cursuri){

        Boolean result= false;
        for (Course curs : cursuri){
            if (id == curs.getCourseID()){
                result = true;
            }
        }


        return result;
    }

    public String getCurseNamebyId(int id,List<Course> cursuri){
        String result= "";
        for (Course curs : cursuri){
            if (id == curs.getCourseID()){
                result = curs.getCourseName();
            }
        }


        return result;
    }

    public Course getCoursebyId(int id,List<Course> cursuri){
        Course result= null;
        for (Course curs : cursuri){
            if (id == curs.getCourseID()){
                result = curs;
            }
        }


        return result;
    }


    public int getNumberofStudentsperClass(int cursId,Map<Course,List<Student>> data){

        int totalnumberofStudents=0;

        Set<Course> listaDeCursuri = data.keySet();

        for (Course curs : listaDeCursuri){

            if (curs.getCourseID() == cursId){

                for ( Student student : data.get(curs)){
                    totalnumberofStudents++;
                }
            }

        }
        return totalnumberofStudents;
    }

//    public int getCursuriDisponibile(int cursId,Map<Course,List<Student>> data){
//        int cursuriDisponibile=0;
//
//        for (Map.Entry<Course,List<Student>> entry : data.entrySet()){
//            if (entry.getValue().size() < 8){
//                cursuriDisponibile++;
//                System.out.println("Curs disponibil: " + entry.getKey());
//            }
//
//        }
//
//
//        }
//        return cursuriDisponibile;


}
