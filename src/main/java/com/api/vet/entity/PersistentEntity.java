package com.api.vet.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author Rodrigo Caro
 */
@MappedSuperclass
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public abstract class PersistentEntity implements Serializable{
    
     @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  //@ApiModelProperty(notes = "An unique UUID for database indetity")
  private String id;

  @CreatedDate
  @Column(name = "timestamps", columnDefinition = "TIMESTAMP")
  //@ApiModelProperty(notes = "Timestamp of creation")
  private LocalDateTime timestamps;

  @Column(name = "soft_delete")
  private boolean softDelete = Boolean.FALSE;
}
