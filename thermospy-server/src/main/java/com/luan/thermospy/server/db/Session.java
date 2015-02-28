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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Session generated by hbm2java
 */
@NamedQueries({
	@NamedQuery(
	name = "Session.findAll",
	query = "from Session s"
	)
})


@Entity
@Table(name = "SESSION")
public class Session  implements java.io.Serializable {

     @Id
     @GenericGenerator(name="id_generator", strategy="increment")
     @GeneratedValue(generator="id_generator", strategy=GenerationType.SEQUENCE)
     @Column(name = "id", unique = true, nullable = false)
     private int id;
     private String name;
     @Column(name="START_TIMESTAMP")
     private Date startTimestamp;
     @Column(name="END_TIMESTAMP")
     private Date endTimestamp;
     
     private String comment;
     private Integer targettemperature;
     private Boolean isopen;

    public Session() {
    }

	
    public Session(int id) {
        this.id = id;
    }
    public Session(int id, String name, Date startTimestamp, Date endTimestamp, String comment, Integer targettemperature, Boolean isopen) {
       this.id = id;
       this.name = name;
       this.startTimestamp = startTimestamp;
       this.endTimestamp = endTimestamp;
       this.comment = comment;
       this.targettemperature = targettemperature;
       this.isopen = isopen;
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
    public Date getStartTimestamp() {
        return this.startTimestamp;
    }
    
    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    public Date getEndTimestamp() {
        return this.endTimestamp;
    }
    
    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getTargettemperature() {
        return this.targettemperature;
    }
    
    public void setTargetTemperature(Integer targettemperature) {
        this.targettemperature = targettemperature;
    }
    public Boolean getIsopen() {
        return this.isopen;
    }
    
    public void setIsOpen(Boolean isopen) {
        this.isopen = isopen;
    }




}


