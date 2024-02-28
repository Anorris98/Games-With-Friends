package Server.Access_Roeles;

import com.fasterxml.jackson.annotation.jsonTypeId;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class AccessRole
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //Role title
    private String name;


}