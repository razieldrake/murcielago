
package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.offsec.domain.Service;
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
@JsonInclude(value=Include.NON_EMPTY)
public class CVEDTO {
	
	@JsonProperty("cveID")
	private String idCVE;
	@JsonProperty("impactScore")
	private float impactScore;
	@JsonProperty("baseScore")
	private float baseScore;
	@JsonProperty("vectorString")
	private String Vector;
	@JsonProperty("description")
	private String description;
	@JsonProperty("attackVector")
	private String attackVector;	
	
	private Service idService;
	
	
	
	public Service getServiceId() {
		return idService;
	}
	public void setServiceid(Service serviceid) {
		this.idService = serviceid;
	}
	/*
	 * NEED to add the date of publishing and the date of the last modified with the correct JSON FORMAT
	 */
	public String getIdCVE() {
		return idCVE;
	}
	
	public String getDescription() {
		return description;
	}
	public void setIdCVE(String idCVE) {
		this.idCVE = idCVE;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	public float getBaseScore() {
		return baseScore;
	}
	public float getImpactScore() {
		return impactScore;
	}
	public String getVector() {
		return Vector;
	}
	public String getAttackVector() {
		return attackVector;
	}
	public Service getIdService() {
		return idService;
	}
	public void setBaseScore(float baseScore) {
		this.baseScore = baseScore;
	}
	public void setImpactScore(float impactScore) {
		this.impactScore = impactScore;
	}
	public void setVector(String vector) {
		Vector = vector;
	}
	public void setAttackVector(String attackVector) {
		this.attackVector = attackVector;
	}
	public void setIdService(Service idService) {
		this.idService = idService;
	}
	

}
