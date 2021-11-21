package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamApp {
    static class Person {
        enum Position {
            ENGINEER, DIRECTOR, MANAGER;
        }

        private String name;
        private int age;
        private Position position;

        public Person(String name, int age, Position position) {
            this.name = name;
            this.age = age;
            this.position = position;
        }
    }

    private static void streamSimpleTask() {
        List<Person> persons = new ArrayList<>(Arrays.asList(
                new Person("Bob1", 35, Person.Position.MANAGER),
                new Person("Bob2", 44, Person.Position.DIRECTOR),
                new Person("Bob3", 25, Person.Position.ENGINEER),
                new Person("Bob4", 42, Person.Position.ENGINEER),
                new Person("Bob5", 55, Person.Position.MANAGER),
                new Person("Bob6", 19, Person.Position.MANAGER),
                new Person("Bob7", 33, Person.Position.ENGINEER),
                new Person("Bob8", 37, Person.Position.MANAGER)
        ));

/**
 * From the list of employees we get a list of the names of engineers.
 */
        {
            List<Person> engineers = new ArrayList<>();
            for (Person o : persons) {
                if (o.position == Person.Position.ENGINEER) {
                    engineers.add(o);
                }
            }
            engineers.sort(new Comparator<Person>() {
                @Override
                public int compare(Person o1, Person o2) {
                    return o1.age - o2.age;
                }
            });
            List<String> engineersNames = new ArrayList<>();
            for (Person o : engineers) {
                engineersNames.add(o.name);
            }
            System.out.println(engineersNames);
        }

/**
 * The same with the Steam API
 */
        {
            List<String> engineersNames = persons.stream()
                    .filter(person -> person.position == Person.Position.ENGINEER)
                    .sorted((o1, o2) -> o1.age - o2.age)
                    .map((Function<Person, String>) person -> person.name)
                    .collect(Collectors.toList());
            System.out.println(engineersNames);
        }
    }

    public static void main(String[] args) {
        StreamApp.streamSimpleTask();
    }
}
