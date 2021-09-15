package Model.shared;

import java.util.ArrayList;

public class Announcements {
    private ArrayList<String> peopleRequest = new ArrayList<>();
    private ArrayList<String> clientRequest = new ArrayList<>();
    private ArrayList<String> system = new ArrayList<>();

    public Announcements(ArrayList<String> peopleRequest, ArrayList<String> clientRequest, ArrayList<String> system) {
        this.peopleRequest = peopleRequest;
        this.clientRequest = clientRequest;
        this.system = system;
    }

    public Announcements() {
    }

    public ArrayList<String> getPeopleRequest() {
        return peopleRequest;
    }

    public void setPeopleRequest(ArrayList<String> peopleRequest) {
        this.peopleRequest = peopleRequest;
    }

    public ArrayList<String> getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(ArrayList<String> clientRequest) {
        this.clientRequest = clientRequest;
    }

    public ArrayList<String> getSystem() {
        return system;
    }

    public void setSystem(ArrayList<String> system) {
        this.system = system;
    }
}
