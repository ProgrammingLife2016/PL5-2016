package metadata;

import java.lang.reflect.Field;

/**
 * Class that contains all the meta Data.
 */
public class GenomeMetadata {

	/** 
	 * The genome id. 
	 */
    private String genomeId;

    /**
     * These variables represent all the meta data.
     */
    private String age;
    private String sex;
    private String hivStatus;
    private String cohort;
    private String dateOfCollection;
    private String geoGraphicDistrict;
    private String specimenType;
    private String smearStatus;
    private String dnaIsolation;
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
    private String xdrType;

    /**
     * Instantiates a new genome meta data.
     *
     * @param genomeId the genome id
     * @param lineage the lineage.
     */
    public GenomeMetadata(String genomeId, String lineage) {
        this.genomeId = genomeId;
        this.lineage = lineage;
    }

	/**
	 * Instantiates a new genome meta data.
     *
     *@param features parsed String array with features.
     */
    public GenomeMetadata(String[] features) {
        genomeId = features[0];
        age = features[1];
        sex = features[2];
        hivStatus = features[3];
        cohort = features[4];
        dateOfCollection = features[5];
        geoGraphicDistrict = features[6];
        specimenType = features[7];
        smearStatus = features[8];
        dnaIsolation = features[9];
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
        xdrType = features[23];
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

    /**
     * getter.
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * getter.
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * getter.
     * @return hivStatus
     */
    public String getHivStatus() {
        return hivStatus;
    }

    /**
     * getter.
     * @return cohort
     */
    public String getCohort() {
        return cohort;
    }

    /**
     * getter.
     * @return dateOfCollection
     */
    public String getDateOfCollection() {
        return dateOfCollection;
    }

    /**
     * getter.
     * @return geoGraphicDistrict
     */
    public String getGeoGraphicDistrict() {
        return geoGraphicDistrict;
    }

    /**
     * getter.
     * @return specimenType
     */
    public String getSpecimenType() {
        return specimenType;
    }

    /**
     * getter.
     * @return smearStatus
     */
    public String getSmearStatus() {
        return smearStatus;
    }

    /**
     * getter.
     * @return dnaIsolation
     */
    public String getDnaIsolation() {
        return dnaIsolation;
    }

    /**
     * getter.
     * @return phenoDSTPattern
     */
    public String getPhenoDSTPattern() {
        return phenoDSTPattern;
    }

    /**
     * getter.
     * @return capreomycin
     */
    public String getCapreomycin() {
        return capreomycin;
    }

    /**
     * getter.
     * @return ethambutol
     */
    public String getEthambutol() {
        return ethambutol;
    }

    /**
     * getter.
     * @return ethionamide
     */
    public String getEthionamide() {
        return ethionamide;
    }

    /**
     * getter.
     * @return isoniazid
     */
    public String getIsoniazid() {
        return isoniazid;
    }

    /**
     * getter.
     * @return kanamyicin
     */
    public String getKanamyicin() {
        return kanamyicin;
    }

    /**
     * getter.
     * @return pyrazinamide
     */
    public String getPyrazinamide() {
        return pyrazinamide;
    }

    /**
     * getter.
     * @return ofloxacin
     */
    public String getOfloxacin() {
        return ofloxacin;
    }

    /**
     * getter.
     * @return rifampin
     */
    public String getRifampin() {
        return rifampin;
    }

    /**
     * getter.
     * @return streptomycin
     */
    public String getStreptomycin() {
        return streptomycin;
    }

    /**
     * getter.
     * @return digitalSpoligotype
     */
    public String getDigitalSpoligotype() {
        return digitalSpoligotype;
    }

    /**
     * getter.
     * @return genoDSTPattern
     */
    public String getGenoDSTPattern() {
        return genoDSTPattern;
    }

    /**
     * getter.
     * @return xdrType
     */
    public String getXdrType() {
        return xdrType;
    }

    /**
     * Returns the value of a field, based on the field's name.
     * @param field the field for which the value is wanted
     * @return the value of the wanted field
     */
    public String returnField(String field) {
        try {
            Field f = this.getClass().getDeclaredField(field);
            return (String) f.get(this);
        } catch (Exception e) {
            System.out.println("field " + field + " does not exist");
            return "";
        }
    }
}
