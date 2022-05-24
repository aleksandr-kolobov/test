import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.format.DateTimeFormatter;

public class Main {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
    public static void main(String[] args) {
        StandardServiceRegistry registry =new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        Student student;
        int i = 1;
        do {
            i++;
            student = session.get(Student.class, i);
            System.out.println(student.getName() + "; курсов: " + student.getCourses().size() + " шт");
        } while (student.getCourses().size() < 1);
        Course course = student.getCourses().get(0);
        Student student1 = session.get(Student.class, 1);
        Course course1 = student1.getCourses().get(0);

        for (i = 1; i < 5; i++) {
            Teacher teacher = session.get(Teacher.class, i);
            System.out.println(teacher.getName() +
                    "; зарплата: " + teacher.getSalary() +
                    "; курсов ведет: " + teacher.getCourses().size());
        }
        for (i = 1; i < 5; i++) {
            Course c = session.get(Course.class, i);
            System.out.println(c.getName() +
                    "; преподаватель: " + c.getTeacher().getName() +
                    "; студенты:" + c.getStudentsCount() + " " + i);
            c.getStudens().forEach(System.out::println);
            System.out.println();
        }
        PurchaseList purchase = session.get(PurchaseList.class,
                new PurchaseList.PurchaseKey(student.getName(), course.getName()));
        System.out.println("Студент: " + purchase.getStudentName() +
                "; курс: " + purchase.getCourseName() +
                "; цена: " + purchase.getPrice());
        purchase = session.get(PurchaseList.class,
                new PurchaseList.PurchaseKey(student1.getName(), course1.getName()));
        System.out.println("Студент: " + purchase.getStudentName() +
                "; курс: " + purchase.getCourseName() +
                "; цена: " + purchase.getPrice());

        sessionFactory.close();
    }
}
