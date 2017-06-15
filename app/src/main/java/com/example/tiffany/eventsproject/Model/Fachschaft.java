package com.example.tiffany.eventsproject.Model;

/**
 * Created by Ich on 09.06.2017.
 */

enum Building
{
    A, B, C, E, F, G, H, K, L, O, P, Q, R, S, T, Y, Z
} ;

enum Faculty
{
    Informationstechnik, Biotechnologie, Maschinenbau, Elektrotechnik, Sozialwesen, Gestaltung, Verfahrenstechnik, Informatik, Wirtschaftsingenieurwesen
} ;

enum Faculty_Letter
{
  N, B, M, E, S, G, V, I, W
};

public class Fachschaft {
    private Long id;
    private Faculty_Letter name;
    private String fsSprecher, member, adress, email, facebook, website, description;
    private Faculty faculty;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.name();
    }

    public void setName(String name) {
        this.name = Faculty_Letter.valueOf(name);
    }

    public String getFsSprecher() {
        return fsSprecher;
    }

    public void setFsSprecher(String fsSprecher) {
        this.fsSprecher = fsSprecher;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member){

        this.member=member;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getFaculty() {
        return this.faculty.name();
    }

    public void setFaculty(String faculty) {
        this.faculty = Faculty.valueOf(faculty);
    }

    public String getFacebook() {
        return this.facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
