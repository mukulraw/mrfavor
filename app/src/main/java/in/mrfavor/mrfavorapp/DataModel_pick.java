package in.mrfavor.mrfavorapp;

public class DataModel_pick {

    String name;
    String type;
    String version_number;
    String feature;

    public DataModel_pick(String name, String type, String version_number, String feature ) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
        this.feature=feature;

    }

    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public String getVersion_number() {
        return version_number;
    }
    
    public String getFeature() {
        return feature;
    }
    
}
