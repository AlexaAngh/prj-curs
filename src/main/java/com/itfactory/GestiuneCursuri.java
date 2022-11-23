package com.itfactory.utils;

import com.itfactory.data.DataLoader;
import com.itfactory.data.DataSaver;
import com.itfactory.model.Course;
import com.itfactory.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class GestiuneCursuri {

    public static Map<Course, List<Student>> data = new HashMap<Course, List<Student>>();
    public static ArrayList<Course> listaCursuri =new ArrayList<Course>();
    public static ArrayList<Student> listaStudenti = new ArrayList<Student>();
    public static int NUMAR_MAXIM_STUDENTI = 8;
    public static ArrayList<Student> listaAsteptare = new ArrayList<Student>();

    public static void main(String[] args) {

        loadData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Help \n" +
                "0 - Ies din program\n" +
                "1 - Afiseaza cursuri\n" +
                "2 - Introduceti un curs nou\n" +
                "3 - Introduceti un student nou si inrolati-l la curs\n" +
                "4 - Cautati un student dupa nume (OPTIONAL)\n" +
                "5 - Afiseaza studentii si cursul la care participa (OPTIONAL)\n");

        while (true) {

            System.out.print("Introduceti optiunea");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 0:
                    System.exit(0);
                    break;

                case 1:
                    afiseazaCursuri();
                    break;

                case 2:
                    creazaCurs(data);
                    break;

                case 3:
                    creazaStudent();
                    break;

                case 4:
                    cautaStudent();
                    break;

                case 5:
                    afiseazaClase();
                    break;

                default:
                    System.out.println("Help \n" +
                            "0 - Ies din program\n" +
                            "1 - Afiseaza cursuri\n" +
                            "2 - Introduceti un curs nou\n" +
                            "3 - Introduceti un student nou si inrolati-l la curs\n" +
                            "4 - Cautati un student dupa nume (OPTIONAL)\n" +
                            "5 - Afiseaza studentii si cursul la care participa (OPTIONAL)\n");

            }
        }

    }

    public static void loadData(){
        System.out.println("loadData");
        DataLoader dataloader = new DataLoader();

        /**
         * initialising classes with no students
         */

        listaCursuri =dataloader.loadCursuri();
        listaStudenti = dataloader.loadStudenti();

        for (Course curs : listaCursuri){
            data.put(curs,new ArrayList<Student>());
        }
        System.out.println("Au fost definite " + listaCursuri.size() + " cursuri");
        System.out.println("Au fost inregistrati " + listaStudenti.size() + " studenti");


        /**
         * adding students to class
         */
        String line;
        try (BufferedReader br = new BufferedReader(
                new FileReader("mapari.csv"))) {


            while ((line = br.readLine()) != null) {

                // split by a comma separator
                String[] split = line.split(",");

                int studentId;
                int cursId;
                //200,100
                studentId = Integer.parseInt(split[0]);
                cursId = Integer.parseInt(split[1]);

                Course curs = dataloader.getCoursebyId(cursId,listaCursuri);
                Student student = dataloader.getStudentbyId(studentId,listaStudenti);

                /**
                 * reading existing students and adding curent in class
                 */
                List<Student> listaStudentilaCurs = data.get(curs);
                listaStudentilaCurs.add(student);
                data.put(curs,listaStudentilaCurs);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void afiseazaCursuri() {




        String cursDisponibil = "";
        Set<Course> listaDeCursuri = data.keySet();
        System.out.println("Lista de cursuri disponibile:\n");
        for (Course curs : listaDeCursuri){
            if (data.get(curs).size() < NUMAR_MAXIM_STUDENTI){
                cursDisponibil = " * Availlable ";
            }else{
                cursDisponibil = "              ";
            }

            System.out.println("Curs " + "\t" + cursDisponibil + curs.getCourseID()  + "\t" + "Pretul: " + curs.getPrice() + "\t" + curs.getCourseName() + "\t"  );
        }
        System.out.println("--------------------------");


    }

    public static void creazaCurs(Map<Course,List<Student>> data){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti numele cursului ");
        String numeCurs =  scanner.nextLine();
        System.out.print("Introduceti pretul ");
        int pretCurs =  Integer.parseInt(scanner.nextLine());


        DataUtils datautils =new DataUtils();
        int newId = datautils.getnextCourseId(listaCursuri);
        Course cursNou = new Course(newId,numeCurs,pretCurs);
        cursNou.setStartDate(LocalDate.now());


        listaCursuri.add(cursNou);
        data.put(cursNou,new ArrayList<Student>());

        DataSaver datasaver = new DataSaver();
        datasaver.saveCursuri(cursNou.toString());

    }

    public static void creazaStudent(){

        Scanner scanner = new Scanner(System.in);
        DataSaver datasaver = new DataSaver();

        System.out.print("Introduceti numele studentului ");
        String numeStudent =  scanner.nextLine();
        System.out.print("Introduceti bugetul studentului ");
        int buget =  Integer.parseInt(scanner.nextLine());



        /**
         * generate new id for Student entry
         */
        DataUtils datautils =new DataUtils();
        int newId = datautils.getnextStudentId(listaStudenti);
        Student studentNou = new Student(newId,numeStudent,buget);

        /**
         * inrolare Student la curs
         */
        System.out.print("Introduceti ID CURS ");
        int idCurs =  Integer.parseInt(scanner.nextLine());

        String newmaping="";
        if (datautils.isValidCourseId(idCurs,listaCursuri) == true){
            newmaping = studentNou.getStudentId() + "," + idCurs;
        }else{
            System.out.println("Nu am gasit nici un curs cu id-ul " + idCurs);
        }


        if (newmaping.length() > 0 ){
            Course cursinrolat = datautils.getCoursebyId(idCurs,listaCursuri);
            System.out.println(datautils.getNumberofStudentsperClass(cursinrolat.getCourseID(),data));

            if (studentNou.getBudget() < cursinrolat.getPrice()) {
                System.out.println("Pretul acestui curs depaseste bugetul tau ");
                System.out.println("Te rugam incearca din nou");
                listaAsteptare.add(studentNou);

            }else if(datautils.getNumberofStudentsperClass(cursinrolat.getCourseID(),data) >= NUMAR_MAXIM_STUDENTI ){
                System.out.println("S-a atins numarul maxim de studenti inrolati la acest curs");
                System.out.println("Te rugam incearca din nou");
                listaAsteptare.add(studentNou);

            }else{
                /**
                 * consuming student budget
                 */
                datasaver.inrolareStudent(newmaping);
                System.out.println("Ati inrolat pe <" + studentNou.getStudentName() + "> la cursul <" + datautils.getCurseNamebyId(idCurs,listaCursuri) + ">.");


                List<Student> listaStudentilaCurs = data.get(cursinrolat);
                listaStudentilaCurs.add(studentNou);
                data.put(cursinrolat,listaStudentilaCurs);


            }
            /**
             * Saveing Student in database, unfortunately without any money better stay at home !!!
             */
            studentNou.setBudget(studentNou.getBudget()-cursinrolat.getPrice());
            listaStudenti.add(studentNou);
            datasaver.saveStudent(studentNou.toString());

        }





    }

    public static void afiseazaClase(){

        Set<Course> listaDeCursuri = data.keySet();

        System.out.println("Lista de cursuri curente:\n");
        for (Course curs : listaDeCursuri){

            System.out.println(curs.getCourseName());
            int i=1;
            for ( Student student : data.get(curs)){
                System.out.println(i +"\t" + student.getStudentName());
                i++;
            }
            if (data.get(curs).size() == 0) {
                System.out.println("Nu sunt studenti inrolati in acest curs");
            }
            System.out.println("--------------------\n");

        }



    }

    public static void cautaStudent(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceti numele ");
        String x =  scanner.nextLine();

        int matchid=-1;
        for (Student student : listaStudenti){
            if (student.getStudentName().contains(x) == true){
                matchid=student.getStudentId();
                System.out.println("Studentul cautat: "+ student.getStudentName());
            }
        }

        if (matchid > 0){
            Set<Course> listaDeCursuri = data.keySet();

            for (Course curs : listaDeCursuri){

                int i=1;
                for ( Student student : data.get(curs)){
                    if (student.getStudentId() == matchid){
                        System.out.println(student.getStudentName() + " is attending " + curs.getCourseName());
                        break;
                    }
                }

            }
            System.out.println("--------------------\n");
        }

    }
}