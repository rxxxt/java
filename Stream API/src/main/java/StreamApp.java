package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
 * Example 1
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
 * Example 2
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
//        StreamApp.streamSimpleTask();

/**
 * Examples
 */
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        {
            // #1
            List<Integer> out = integers.stream().filter(new Predicate<Integer>() {
                @Override
                public boolean test(Integer integer) {
                    return integer % 2 == 0;
                }
            }).collect(Collectors.toList());
            System.out.println(out);

            // #2
            integers.stream()
                    .filter((n) -> n % 2 == 1)
                    .forEach(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            System.out.println(integer);
                        }
                    });

            // #3
            Stream.of("AA", "BBB", "C", "DDDD").map(new Function<String, Integer>() {
                @Override
                public Integer apply(String s) {
                    return s.length();
                }
            }).forEach(System.out::println);
        }
/**
 * The same with the Steam API
 */
        {
            // #1
            List<Integer> out = integers.stream()
                    .filter((n) -> n % 2 == 0)
                    .collect(Collectors.toList());
            System.out.println(out);


            // #2
            integers.stream()
                    .filter((n) -> n % 2 == 1)
//                    .forEach(integer -> System.out.println(integer));
                    .forEach(System.out::println);

            // #3
            Stream.of("AA", "BBB", "C", "DDDD")
                    .map(String::length)
                    .forEach(System.out::println);

            // #4
            class User {
                String name;

                public User(String name) {
                    this.name = name;
                }
            }
            Stream.of("Bob", "Max", "John")
                    .map(User::new)
                    .collect(Collectors.toList());

            {
                // находим уникальные значения
                Arrays.asList(1, 2, 3, 4, 4, 3, 2, 3, 2, 1).stream().distinct().forEach(System.out::println);
            }

            {
                // совпадения
                System.out.println("Совпадения");
                List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
                System.out.println(list.stream().allMatch(n -> n < 10));
                System.out.println(list.stream().anyMatch(n -> n == 4));
                System.out.println(list.stream().noneMatch(n -> n == 2));

                Function<String, Integer> _strToLen = String::length;
                Function<String, Integer> strToLen = s -> s.length();
                Predicate<Integer> evenNumberFilter = n -> n % 2 == 0;
                Function<Integer, Integer> cube = n -> n * n * n;
                Stream.of(1, 2, 3).map(n -> Math.pow(n, 3));
                List<String> list1 = Arrays.asList("AA", "BBB", "C", "DDDD", "EEEEE");
                List<Integer> wordsLength = list1.stream().map(strToLen).collect(Collectors.toList());

                System.out.println(list1);
                System.out.println(wordsLength);

                list1.stream().map(strToLen).forEach(System.out::println);
            }

            // поиск
            {
                System.out.println("*********************");
                List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
                list.stream().filter(n -> n > 10).findAny().ifPresent(System.out::println);

                Optional<Integer> opt = list.stream().filter(n -> n > 10).findAny();
                if (opt.isPresent()) {
                    System.out.println(opt.get());
                }
                System.out.println("*********************");
            }

            // reduce
            {
                List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
                int sum = 0;
                for (Integer o : list) {
                    sum += o;
                }
//                int streamSum = list.stream().reduce(0, (a, b) -> a + b);
                int streamSum = list.stream().reduce(0, Integer::sum);
                System.out.println(sum + " " + streamSum);
            }

            // intStream
            {
                System.out.println("IntStream");
                IntStream myIntStream = IntStream.of(10, 20, 30, 40, 50);
                List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
                list.stream().mapToInt(v -> v).sum();
                IntStream.rangeClosed(2, 10).filter(n -> n % 2 == 0).forEach(System.out::println);
            }

            // streamFrom
            {
                System.out.println("streamFrom");
                try {
                    Files.lines(Paths.get("text.txt")).map(String::length).forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // simpleString
            {
                System.out.println("simple String");
                System.out.println(Arrays.stream("A B CC B AA A A B CC C".split("\\s")).distinct().count());
            }

            {
                System.out.println("More examples");
                try {
                    Files.lines(Paths.get("text1.txt"))
                            .map(line -> line.split("\\s"))
                            .distinct()
                            .forEach(arr -> System.out.println(Arrays.toString(arr)));
                    System.out.println("--------------------------------------");
                    Files.lines(Paths.get("text1.txt"))
                            .map(line -> line.split("\\s"))
                            .map(Arrays::stream)
                            .distinct()
                            .forEach(System.out::println);
                    System.out.println("--------------------------------------");
                    System.out.println(Files.lines(Paths.get("text1.txt"))
                            .map(line -> line.split("\\s"))
                            .flatMap(Arrays::stream)
                            .distinct()
                            .collect(Collectors.joining(", ", "Униклаьные слова: ", ".")));
                    System.out.println("--------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Исключеине в промежуточной операции
                {
                    Stream.of(1, 2, 3, 4, 5, 6, 7).filter(n ->myOperation(n, 0)).collect(Collectors.toList());
                }
            }
        }

/**
 * Example 3
 * Stream from List collections using the stream() method
 */
        {
            List<String> list = new ArrayList<>(Arrays.asList(" A", " AB", " B") );
            Stream<String> stream = list.stream();
        }

/**
 * Example 4
 * Stream from List collections using the static Stream.of() method
 */
        {
            Stream<Integer> stream = Stream.of(1, 2, 3, 4 );
        }

    }
    public static boolean myOperation(int n, int coef) {
        try {
            return n / coef < 10;
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return false;
        }
    }
}
