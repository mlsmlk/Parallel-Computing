package ca.mcgill.ecse420.a3;

public class FineGrainedAlgorithmTest {

    public static void main(String[] args){
        FineGrainedAlgorithm<Integer> newList = new FineGrainedAlgorithm<Integer>();
        newList.add(3);
        newList.add(6);
        newList.add(9);
        newList.add(4);
        newList.add(43);
        newList.add(57);
        System.out.println("\nCheck contains() for 3:\nExpected: true\nObtained: " + newList.contains(3));
        System.out.println("\nCheck contains() for 41:\nExpected: false\nObtained: "+ newList.contains(41));
        System.out.println("\nCheck contains() for 57:\nExpected: true\nObtained: " + newList.contains(57));
        System.out.println("\nCheck contains() for 513:\nExpected: false\nObtained: "+ newList.contains(513));
    }

}
