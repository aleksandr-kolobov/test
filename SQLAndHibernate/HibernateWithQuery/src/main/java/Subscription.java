import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Embeddable
    public static class SubscriptionsKey implements Serializable {
        @Column(name = "student_id")
        private Integer studentID;
        @Column(name = "course_id")
        private Integer courseID;

        public SubscriptionsKey() {
        }
        public SubscriptionsKey(Integer studentID, Integer courseID) {
            this.studentID = studentID;
            this.courseID = courseID;
        }
        public Integer getStudentID() {
            return studentID;
        }
        public Integer getCourseID() {
            return courseID;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SubscriptionsKey that = (SubscriptionsKey) o;
            return studentID.equals(that.studentID) && courseID.equals(that.courseID);
        }
        @Override
        public int hashCode() {
            return Objects.hash(studentID, courseID);
        }
    }

    @EmbeddedId
    private Subscription.SubscriptionsKey key;
    @Column(name = "student_id", insertable = false, updatable = false)
    private Integer studentID;
    @Column(name = "course_id", insertable = false, updatable = false)
    private Integer courseID;
    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    public SubscriptionsKey getKey() {
        return key;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

}
