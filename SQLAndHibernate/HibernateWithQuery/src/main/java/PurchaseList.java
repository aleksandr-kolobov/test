import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "purchaselist")
public class PurchaseList {

    @Embeddable
    public static class PurchaseKey implements Serializable {
        @Column(name = "student_name")
        private String studentName;
        @Column(name = "course_name")
        private String courseName;

        public PurchaseKey() {
        }
        public PurchaseKey(String studentName, String courseName) {
            this.studentName = studentName;
            this.courseName = courseName;
        }
        public String getStudentName() {
            return studentName;
        }
        public String getCourseName() {
            return courseName;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PurchaseKey that = (PurchaseKey) o;
            return studentName.equals(that.studentName) && courseName.equals(that.courseName);
        }
        @Override
        public int hashCode() {
            return Objects.hash(studentName, courseName);
        }
    }

    @EmbeddedId
    private PurchaseList.PurchaseKey key;
    @Column(name = "student_name", insertable = false, updatable = false)
    private String studentName;
    @Column(name = "course_name", insertable = false, updatable = false)
    private String courseName;
    private Integer price;
    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public void setKey(PurchaseKey key) {
        this.key = key;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getPrice() {
        return price;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }
}
