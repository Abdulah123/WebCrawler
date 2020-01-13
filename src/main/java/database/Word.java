package database;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "words")
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
public class Word {

     @Id
     @Column(name = "id")
     @GeneratedValue
     private Long id;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "counts")
    private String counts;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Calendar time;


    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Word)) {
            return false;
        }
        Word other = (Word) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        return result;
    }
}



