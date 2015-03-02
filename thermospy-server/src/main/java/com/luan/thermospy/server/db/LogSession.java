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
 * LogSession generated by hbm2java
 */
@NamedQueries({
	@NamedQuery(
	name = "LogSession.findAll",
	query = "from LogSession s"
	)
})


@Entity
@Table(name = "SESSION")
public class LogSession  implements java.io.Serializable {

     @Id
     @GenericGenerator(name="id_generator", strategy="increment")
     @GeneratedValue(generator="id_generator", strategy=GenerationType.SEQUENCE)
     @Column(name = "id", unique = true, nullable = false)
     private int id;
     @Column(name="name")
     private String name;
     @Column(name="START_TIMESTAMP")
     private Date startTimestamp;
     @Column(name="END_TIMESTAMP")
     private Date endTimestamp;
     
     @Column(name="comment")
     private String comment;
     @Column(name="TARGETTEMPERATURE")
     private Integer targetTemperature;
     @Column(name="isopen")
     private Boolean isopen;

    public LogSession() {
    }

	
    public LogSession(int id) {
        this.id = id;
    }
    public LogSession(int id, String name, Date startTimestamp, Date endTimestamp, String comment, Integer targetTemperature, Boolean isopen) {
       this.id = id;
       this.name = name;
       this.startTimestamp = startTimestamp;
       this.endTimestamp = endTimestamp;
       this.comment = comment;
       this.targetTemperature = targetTemperature;
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
        return this.targetTemperature;
    }
    
    public void setTargetTemperature(Integer targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
    public Boolean getIsopen() {
        return this.isopen;
    }
    
    public void setIsOpen(Boolean isopen) {
        this.isopen = isopen;
    }




}


