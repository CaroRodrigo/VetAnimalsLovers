package com.api.vet.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

/**
 *
 * @author Rodrigo Caro
 */
@Entity
@Table(name = "images")
@SQLDelete(sql = "UPDATE imagess SET soft_delete = true WHERE id=?")
@FilterDef(name = "deletedImageFilter",
        parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedImageFilter", condition = "soft_delete = :isDeleted")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Image extends PersistentEntity{
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "mime")
    private String mime;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] contents;
}
