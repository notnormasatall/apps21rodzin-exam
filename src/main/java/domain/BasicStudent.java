package domain;

import json.*;

/**
 * Created by Andrii_Rodionov on 1/5/2017.
 */
public class BasicStudent implements Jsonable {

    protected String name;
    protected String surname;
    protected Integer year;

    public BasicStudent(String name, String surname, Integer year) {
        this.name = name;
        this.surname = surname;
        this.year = year;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject student = new JsonObject();

        student.add(new JsonPair("name", new JsonString(this.name)));
        student.add(new JsonPair("surname", new JsonString(this.surname)));
        student.add(new JsonPair("year", new JsonNumber(this.year)));

        return student;
    }
}
