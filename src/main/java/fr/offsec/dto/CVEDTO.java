package fr.offsec.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
	
	private String idCVE;			private float baseScoreV3;		private float impactScoreV3;
	private float baseScoreV2;		private float impactScoreV2;	private String VectorV3;
	private String VectorV2;		private String description;
	private String attackVectorV2;	private String attackVectorV3;	private Service serviceid;
	public Service getServiceid() {
		return serviceid;
	}
	public void setServiceid(Service serviceid) {
		this.serviceid = serviceid;
	}
	/*
	 * NEED to add the date of publishing and the date of the last modified with the correct JSON FORMAT
	 */
	public String getIdCVE() {
		return idCVE;
	}
	public float getBaseScoreV3() {
		return baseScoreV3;
	}
	public float getImpactScoreV3() {
		return impactScoreV3;
	}
	public float getBaseScoreV2() {
		return baseScoreV2;
	}
	public float getImpactScoreV2() {
		return impactScoreV2;
	}
	public String getVectorV3() {
		return VectorV3;
	}
	public String getVectorV2() {
		return VectorV2;
	}
	public String getDescription() {
		return description;
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
	public void setBaseScoreV2(float baseScoreV2) {
		this.baseScoreV2 = baseScoreV2;
	}
	public void setImpactScoreV2(float impactScoreV2) {
		this.impactScoreV2 = impactScoreV2;
	}
	public void setVectorV3(String vectorV3) {
		VectorV3 = vectorV3;
	}
	public void setVectorV2(String vectorV2) {
		VectorV2 = vectorV2;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAttackVectorV2() {
		return attackVectorV2;
	}
	public String getAttackVectorV3() {
		return attackVectorV3;
	}
	public void setAttackVectorV2(String attackVectorV2) {
		this.attackVectorV2 = attackVectorV2;
	}
	public void setAttackVectorV3(String attackVectorV3) {
		this.attackVectorV3 = attackVectorV3;
	}

}
