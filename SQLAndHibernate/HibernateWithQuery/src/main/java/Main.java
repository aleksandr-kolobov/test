import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StandardServiceRegistry registry =new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PurchaseList> query = builder.createQuery(PurchaseList.class);
        Root<PurchaseList> root = query.from(PurchaseList.class);
        query.select(root);

        List<PurchaseList> purchaseList = session.createQuery(query).list();
        for (PurchaseList purchase : purchaseList) {
            Student student = session.byNaturalId(Student.class)
                    .using("name", purchase.getStudentName()).load();
            Course course = session.byNaturalId(Course.class)
                    .using("name", purchase.getCourseName()).load();
            //System.out.println(student.getName() + " - " + course.getName());
            LinkedPurchaseList linkedPurchase = new LinkedPurchaseList();
            linkedPurchase.setKey(new LinkedPurchaseList.LPLKey(student.getId(), course.getId()));
            linkedPurchase.setStudentID(student.getId());
            linkedPurchase.setCourseID(course.getId());
            session.save(linkedPurchase);
        }
            /*Student student1 = session.get(Student.class, 1);
            Course course1 = student1.getCourses().get(0);
            PurchaseList purchase = session.get(PurchaseList.class,
                    new PurchaseList.PurchaseKey(student1.getName(), course1.getName()));
            System.out.println("Студент: " + purchase.getStudentName() +
                    "; курс: " + purchase.getCourseName() +
                    "; цена: " + purchase.getPrice());*/

        transaction.commit();
        sessionFactory.close();
    }
}
