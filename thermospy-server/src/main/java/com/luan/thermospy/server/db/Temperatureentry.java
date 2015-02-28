package com.luan.thermospy.server.db;
// Generated 2015-feb-22 22:32:42 by Hibernate Tools 4.3.1


import java.util.Date;
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
 * Temperatureentry generated by hbm2java
 */
@NamedQueries({
	@NamedQuery(
	name = "Temperatureentry.findBySessionId",
	query = "from Temperatureentry s where s.fkSessionId = :fkSessionId"
	)
})


@Entity
@Table(name = "TEMPERATUREENTRY")
public class Temperatureentry  implements java.io.Serializable {

     @Id
     @GenericGenerator(name="id_generator", strategy="increment")
     @GeneratedValue(generator="id_generator", strategy=GenerationType.SEQUENCE)
     @Column(name = "id", unique = true, nullable = false)
     private int id;
     
     private Date timestamp;
     private Double temperature;
     @Column(name="FK_SESSION_ID")
     private Integer fkSessionId;

    public Temperatureentry() {
    }

	
    public Temperatureentry(int id) {
        this.id = id;
    }
    public Temperatureentry(int id, Double temperature, Integer fkSessionId) {
       this.id = id;
       this.temperature = temperature;
       this.fkSessionId = fkSessionId;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Date getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public Double getTemperature() {
        return this.temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public Integer getFkSessionId() {
        return this.fkSessionId;
    }
    
    public void setFkSessionId(Integer fkSessionId) {
        this.fkSessionId = fkSessionId;
    }




}


