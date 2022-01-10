package domain;

import json.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class Student extends BasicStudent {
    private List<Tuple<String, Integer>> studentExams;

    public Student(String name, String surname, Integer year, Tuple<String, Integer>... exams) {
        super(name, surname, year);
        studentExams = new ArrayList<>(){};
        studentExams.addAll(Arrays.asList(exams));
    }

    @Override
    public JsonObject toJsonObject() {
        List<JsonObject> exams;
        exams = new ArrayList<>(){};
        JsonObject student = new JsonObject();

        for (Tuple<String, Integer> exam: studentExams) {
            JsonPair course = new JsonPair("course", new JsonString(exam.key));
            JsonPair mark = new JsonPair("mark", new JsonNumber(exam.value));
            JsonPair ifPassed = new JsonPair("passed", new JsonBoolean(exam.value >= 3));

            exams.add(new JsonObject(course, mark, ifPassed));
        }

        student.add(new JsonPair("name", new JsonString(this.name)));
        student.add(new JsonPair("surname", new JsonString(this.surname)));
        student.add(new JsonPair("year", new JsonNumber(this.year)));

        student.add(new JsonPair("exams", new JsonArray(exams.toArray(new Json[exams.size()]))));
        return student;
    }
}