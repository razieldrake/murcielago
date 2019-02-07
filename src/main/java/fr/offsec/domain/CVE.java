package fr.offsec.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * 
 * @author svayssier
 * 
 * CVE constructor
 * idCVE : name of the cve
 * basescorev3 :
 * impact score v3:
 * basescorev2 :
 * impactscorev3:
 * VectorV3:
 * VectorV2:
 * AttackVectorV2:
 * AttackVectorV3:
 * publishdate:
 * lastmodified:
 * description
 *
 *
 */
@Entity
@Table(name="CVE")
@JsonInclude(value = Include.NON_DEFAULT)
public class CVE {
	

	@JsonProperty
	@Id
	private String idCVE;
	
	@JsonProperty
	private float baseScoreV3;
	
	@JsonProperty
	private float impactScoreV3;
	
	@JsonProperty
	private String vectorV3;
	
	@JsonProperty
	private String attackVectorV3;
	
	@JsonProperty
	private float baseScoreV2;

	@JsonProperty
	private float impactScoreV2;
	
	@JsonProperty
	private String vectorv2;
	
	@JsonProperty
	private String attackvectorV2;
	
	/* Need to create publishdate and lastmodified date with json format
	 * Not yet implemented
	 */
	
	@JsonProperty
	private String descCVE;
	
	//@JsonManagedReference
	//@JsonBackReference
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_service", nullable=false)
	@JsonIgnoreProperties("service")
	private Service service;
	
	
	protected CVE() {
		
	}
	@JsonCreator
	public CVE (@JsonProperty("id_cve") String id,@JsonProperty("base_score_v2")float bScoreV2,
				@JsonProperty("base_score_v3") float bScoreV3,@JsonProperty("impact_score_v2")float iScoreV2,
				@JsonProperty("impact_score_v3")float iScoreV3,@JsonProperty("vector_v2")String vector2,
				@JsonProperty("vector_v3")String vector3,@JsonProperty("attack_vector_v2")String attackVector2,
				@JsonProperty("attack_vector_v3")String attackVector3,@JsonProperty("desc_cve") String descr) {
		Assert.hasText(id, "id cannot be blank");
		Assert.notNull(id,"id cannot be null");
		//Assert.isTrue(score >0, "Score cannot be negative or null bnevcause it willbe not an CVE if score is at 0");
		Assert.notNull(descr,"description cannot be null");
		Assert.hasText(descr,"A description cve cannot be empty or blank, please fill the fields");
		
		this.idCVE = id;						this.attackvectorV2 = attackVector2;	this.baseScoreV3 = bScoreV3;
		this.attackVectorV3 = attackVector3;	this.baseScoreV2 = bScoreV2;			this.impactScoreV2 = iScoreV2;
		this.descCVE = descr;					this.impactScoreV3 = iScoreV3;			this.vectorv2 = vector2;
		this.vectorV3 = vector3;				//this.service = service;		
				
				
	}



	public String getIdCVE() {
		return idCVE;
	}



	public float getBaseScoreV3() {
		return baseScoreV3;
	}



	public float getImpactScoreV3() {
		return impactScoreV3;
	}



	public String getVectorV3() {
		return vectorV3;
	}



	public String getAttackVectorV3() {
		return attackVectorV3;
	}



	public float getBaseScoreV2() {
		return baseScoreV2;
	}



	public float getImpactScoreV2() {
		return impactScoreV2;
	}



	public String getVectorv2() {
		return vectorv2;
	}



	public String getAttackvectorV2() {
		return attackvectorV2;
	}



	public String getDescCVE() {
		return descCVE;
	}



	public Service getService() {
		return service;
	}



	public void setIdCVE(String idCVE) {
		this.idCVE = idCVE;
	}



	public void setBaseScoreV3(float baseScoreV3) {
		this.baseScoreV3 = baseScoreV3;
	}



	public void setImpactScoreV3(float impactScoreV3) {
		this.impactScoreV3 = impactScoreV3;
	}



	public void setVectorV3(String vectorV3) {
		this.vectorV3 = vectorV3;
	}



	public void setAttackVectorV3(String attackVectorV3) {
		this.attackVectorV3 = attackVectorV3;
	}



	public void setBaseScoreV2(float baseScoreV2) {
		this.baseScoreV2 = baseScoreV2;
	}



	public void setImpactScoreV2(float impactScoreV2) {
		this.impactScoreV2 = impactScoreV2;
	}



	public void setVectorv2(String vectorv2) {
		this.vectorv2 = vectorv2;
	}



	public void setAttackvectorV2(String attackvectorV2) {
		this.attackvectorV2 = attackvectorV2;
	}



	public void setDescCVE(String descCVE) {
		this.descCVE = descCVE;
	}



	public void setService(Service service) {
		this.service = service;
	}
	
	
	
	

}
