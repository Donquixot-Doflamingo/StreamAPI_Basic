package com.basicStreamAPI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
    static List<Employees> employees = new ArrayList<>();
    static {
        employees.add(
                new Employees("Donquixote","Doflamingo",5000.0,List.of("Marineford","DressRosa"))
        );
        employees.add(
                new Employees("Zoro","Roronoa",5500.0,List.of("DressRosa","WanoKuni"))
        );
        employees.add(
                new Employees("Luffy","Monkey D.",6000.0,List.of("WanoKuni","WholeCake"))
        );
        employees.add(
                new Employees("Shanks","Mystery",7000.0,List.of("EastBlue","Marineford","WanoKuni"))
        );
    }

    public static void basicOfStream(){
        /** Making the stream object and all the actions we are going to perform are using this Stream object
         * there are two ways to create a stream object */
        // 1.
        Stream.of(employees);

        // 2.
        employees.stream();
    }

    public static void forEachOperationTerminateOperation(){

        /** Using terminal operation on the stream objects after performing these operations we can't do anything after that
         * 1. ForEach operation uses a consumer and as a lambda expression
         * -> example 1 to perform single lambda operation */
        employees.stream()
                .forEach(employee -> System.out.println(employee));

        /*** -> example 2 to perform multiple lambda operation */
        employees.stream()
                .forEach(employee -> {
                            System.out.println(employee.getFirstName() + " " + employee.getLastName());
                            System.out.println("Current Salary is -: " + employee.getSalary());
                        }
                );
    }

    /** intermediate operations
     * Using intermediate operations these operations are the one that used to create manipulate and perform
     other operations and this process is done using maps. these operation returns Stream at the end
     */

    /** MAPS Operation
     * Why maps? because it allows us to map a particular object into a different type of object and
     return stream at the end
     * 1. creating new employees with 10%hike of salary and after changing it we are going to collect it in the
     list as the map will only result in the stream. so we have to convert it into a collection to view it */

    public static void mapsOperation(){

        List<Employees> increasedSalary = employees.stream()
                .map(employee -> new Employees(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.1,
                        employee.getProjects()
                ))
                .collect(Collectors.toList());

        System.out.println(increasedSalary);
    }

    /** FILTER Operation
     * Using filter operation we filter the data which is kind of if else condition but here it is filter
     * it is also the intermediate operation as it also returns the stream itself
     */

    public static void filterOperation(){

        List<Employees> increasedFilteredSalary = employees.stream()
                .filter(employees1 -> employees1.getSalary() > 5000)
                .map(employee -> new Employees(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.1,
                        employee.getProjects()
                ))
                .collect(Collectors.toList());

        System.out.println("Filtered salary -> " + increasedFilteredSalary);
    }

    /** FIND-FIRST Operation
     *  The find first returns the first element
     * it returns an optional value means it can either have the value or it can't.
     So, we have to define if the value is null by any chance we will use the case
     orElse and define the empty case*/

    public static void findFirstOperation(){

        Employees FirstEmployee = employees.stream()
                .filter(employees1 -> employees1.getSalary() > 5000)
                .map(employee -> new Employees(
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getSalary() * 1.1,
                        employee.getProjects()
                ))
                .findFirst()
                .orElse(null);
        System.out.println("find first operation -> " + FirstEmployee);
    }

    /** FLAT MAP Operation
      It is used to flatten up the object that we have exmaple we have the list of string then flat map will give us
        stream of strings we won't get stream ofd list of strings.
        If we have List<List<String>> then we will get List<String>.a
     */

    public static void flatMapOperation(){

        String projects = employees
                .stream() // now here it will be having stream of list of employees
                .map(employee -> employee.getProjects())// now here it will be having stream of list of projects
                .flatMap(strings -> strings.stream()) // now here it is Stream of strings
                .collect(Collectors.joining(","));// now we are collecting all the strings seprated by the ','

        System.out.println(projects);
    }

    /** SHORT CIRCUIT Operation
     * It will limit our data
     * skip -> will skip the number of data list
     * limit -> will limit the numbver of list we want
    */

    public  static  void shortCircuit(){
        List<Employees> shortCircuitList = employees
                .stream()
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());

        System.out.println(shortCircuitList);
    }

    /** FINITE DATA Operation
     * It will help us make are infinite data finite by the use of limit
     * limit -> will limit the numbver of list we want
     */
    public  static  void finiteData(){
        Stream.generate(Math::random)
                .limit(5)
                .forEach(value -> System.out.println(value));
    }

    /** SORTING Operation
     * To sort the given data
     */

    public  static  void Sorting(){
        List<Employees> sortedEmployees = employees
                .stream()
                .sorted((o1, o2) -> o1.getFirstName()
                        .compareToIgnoreCase(o2.getFirstName()))
                .collect(Collectors.toList());
        System.out.println(sortedEmployees);
    }

    /** AGGREGATION Operation
     * Min / Max -: To get min and max number of data
     * Reduce -: To convert or accumulate everything like to add of salary or multiplication or anything like that
     */

    public  static  void aggregation(){
        // MAXIMUM
        Employees maximumSalary = employees
                .stream()
                .max(Comparator.comparing(Employees::getSalary))
                .orElseThrow(NoSuchElementException::new);

        System.out.println("maximum Salary -: " + maximumSalary.getSalary());

        // Minimum
        Employees minimumSalary = employees
                .stream()
                .min(Comparator.comparing(Employees::getSalary))
                .orElseThrow(NoSuchElementException::new);

        System.out.println("minimum Salary -: " + minimumSalary.getSalary());

        // Reduce -> Sum operation
        Double totalSalary = employees
                .stream()
                .map(employees1 -> employees1.getSalary())
                .reduce(0.0,Double::sum);

        System.out.println("total Salary -: " + totalSalary);
    }
    public static void main(String[] args) {
        aggregation();
    }
}
