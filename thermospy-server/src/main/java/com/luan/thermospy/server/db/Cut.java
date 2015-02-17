package com.luan.thermospy.server.db;
// Generated 2015-feb-14 09:17:56 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;




/**
 * Cut generated by hbm2java
 */
@NamedQueries({
	@NamedQuery(
	name = "Cut.findAll",
	query = "from Cut s"
	)
})


@Entity
@Table(name = "CUT")
public class Cut  implements java.io.Serializable {

     @Id
     @GenericGenerator(name="id_generator", strategy="increment")
     @GeneratedValue(generator="id_generator", strategy=GenerationType.SEQUENCE)
     @Column(name = "id", unique = true, nullable = false)
     private int id;
     private String name;

    public Cut() {
    }

	
    public Cut(int id) {
        this.id = id;
    }
    public Cut(int id, String name) {
       this.id = id;
       this.name = name;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


