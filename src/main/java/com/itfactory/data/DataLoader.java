package com.itfactory.data;

import com.itfactory.model.Course;
import com.itfactory.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {

    //Map<Course, List<Student>> data = new HashMap<Course, List<Student>>();

    public ArrayList<Course> loadCursuri() {
        ArrayList<Course> listCourses = new ArrayList<Course>();

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("cursuri.csv"))) {

            while ((line = br.readLine()) != null) {

                // split by a comma separator
                String[] split = line.split(",");
                int courseID;
                String courseName;
                double price;
                LocalDate startDate;

                courseID = Integer.parseInt(split[0]);
                courseName = split[1];
                price = Double.parseDouble(split[2]);
                startDate = LocalDate.parse(split[3]);

                Course curs = new Course(courseID, courseName, price);
                curs.setStartDate(startDate);
                listCourses.add(curs);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listCourses;

    }

    public ArrayList<Student> loadStudenti() {
        ArrayList<Student> listStudents = new ArrayList<Student>();

        String line;
        try (BufferedReader br = new BufferedReader(
                new FileReader("studenti.csv"))) {

            while ((line = br.readLine()) != null) {

                // split by a comma separator
                String[] split = line.split(",");

                int studentId;
                String studentName;
                double budget;

                studentId = Integer.parseInt(split[0]);
                studentName = split[1];
                budget = Double.parseDouble(split[2]);


                Student student = new Student(studentId,studentName,budget);
                listStudents.add(student);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listStudents;

    }

    public Student getStudentbyId(int id,ArrayList<Student> listaStudent){
        boolean found=false;
        Student s = null;

        for (Student student : listaStudent){
            if (id == student.getStudentId()){
                found=true;
                s = student;
                break;
            }
        }

        if (found == true){
            return s;
        }else{
            return null;
        }

    }
    public Course getCoursebyId(int id,ArrayList<Course> listaCursuri){
        boolean found=false;
        Course c = null;

        for (Course curs : listaCursuri){
            if (id == curs.getCourseID()){
                found=true;
                c = curs;
                break;
            }
        }

        if (found == true){
            return c;
        }else{
            return null;
        }

    }

}
