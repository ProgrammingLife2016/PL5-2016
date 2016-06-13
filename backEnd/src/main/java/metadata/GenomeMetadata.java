package metadata;

/**
 * The Class GenomeMetadata.
 */
public class GenomeMetadata {


	/** 
	 * The genome id. 
	 */
    private String genomeId;

    private String age;
    private String sex;
    private String HIVStatus;
    private String cohort;
    private String dateOfCollection;
    private String geoGraphicDistrict;
    private String specimenType;
    private String smearStatus;
    private String DNAIsolation;
    private String phenoDSTPattern;
    private String capreomycin;
    private String ethambutol;
    private String ethionamide;
    private String isoniazid;
    private String kanamyicin;
    private String pyrazinamide;
    private String ofloxacin;
    private String rifampin;
    private String streptomycin;
    private String digitalSpoligotype;
    private String lineage;
    private String genoDSTPattern;
    private String XDRType;




	/**
	 * Instantiates a new genome metadata.
     *
     *@param features parsed String array with features.
     */
    public GenomeMetadata(String[] features) {
        genomeId = features[0];
        age = features[1];
        sex = features[2];
        HIVStatus = features[3];
        cohort = features[4];
        dateOfCollection = features[5];
        geoGraphicDistrict = features[6];
        specimenType = features[7];
        smearStatus = features[8];
        DNAIsolation = features[9];
        phenoDSTPattern = features[10];
        capreomycin = features[11];
        ethambutol = features[12];
        ethionamide = features[13];
        isoniazid = features[14];
        kanamyicin = features[15];
        pyrazinamide = features[16];
        ofloxacin = features[17];
        rifampin = features[18];
        streptomycin = features[19];
        digitalSpoligotype = features[20];
        lineage = features[21];
        genoDSTPattern = features[22];
        XDRType = features[23];
    }

	/**
	 * Gets the lineage.
	 *
	 * @return the lineage
	 */
	public String getLineage() {
		return lineage;
	}

	/**
	 * Gets the genome id.
	 *
	 * @return the genome id
	 */
	public String getGenomeId() {
		return genomeId;
	}


    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getHIVStatus() {
        return HIVStatus;
    }

    public String getCohort() {
        return cohort;
    }

    public String getDateOfCollection() {
        return dateOfCollection;
    }

    public String getGeoGraphicDistrict() {
        return geoGraphicDistrict;
    }

    public String getSpecimenType() {
        return specimenType;
    }

    public String getSmearStatus() {
        return smearStatus;
    }

    public String getDNAIsolation() {
        return DNAIsolation;
    }

    public String getPhenoDSTPattern() {
        return phenoDSTPattern;
    }

    public String getCapreomycin() {
        return capreomycin;
    }

    public String getEthambutol() {
        return ethambutol;
    }

    public String getEthionamide() {
        return ethionamide;
    }

    public String getIsoniazid() {
        return isoniazid;
    }

    public String getKanamyicin() {
        return kanamyicin;
    }

    public String getPyrazinamide() {
        return pyrazinamide;
    }

    public String getOfloxacin() {
        return ofloxacin;
    }

    public String getRifampin() {
        return rifampin;
    }

    public String getStreptomycin() {
        return streptomycin;
    }

    public String getDigitalSpoligotype() {
        return digitalSpoligotype;
    }

    public String getGenoDSTPattern() {
        return genoDSTPattern;
    }

    public String getXDRType() {
        return XDRType;
    }
}
