import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "linkedpurchaselist")
public class LinkedPurchaseList {
    @Embeddable
    public static class LPLKey implements Serializable {
        @Column(name = "student_id")
        private Integer studentID;
        @Column(name = "course_id")
        private Integer courseID;

        public LPLKey() {
        }
        public LPLKey(Integer studentID, Integer courseID) {
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
            LPLKey lplKey = (LPLKey) o;
            return studentID.equals(lplKey.studentID) && courseID.equals(lplKey.courseID);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentID, courseID);
        }
    }

    @EmbeddedId
    private LinkedPurchaseList.LPLKey key;
    @Column(name = "student_id", insertable = false, updatable = false)
    private Integer studentID;
    @Column(name = "course_id", insertable = false, updatable = false)
    private Integer courseID;

    public void setKey(LPLKey key) {
        this.key = key;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public LinkedPurchaseList.LPLKey getKey() {
        return key;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer getCourseID() {
        return courseID;
    }
}
