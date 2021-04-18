package ca.mcgill.ecse420.a3;

public class FineGrainedAlgorithmTest {

    public static void main(String[] args){
        FineGrainedAlgorithm<Integer> newList = new FineGrainedAlgorithm<Integer>();
        newList.add(8);
        newList.add(7);
        newList.add(3);
        newList.add(15);
        newList.add(34);
        newList.add(105);
        System.out.println("\nCheck contains() for 8:\nExpected: true\nObtained: " + newList.contains(8));
        System.out.println("\nCheck contains() for 14:\nExpected: false\nObtained: "+ newList.contains(14));
        System.out.println("\nCheck contains() for 15:\nExpected: true\nObtained: " + newList.contains(15));
        System.out.println("\nCheck contains() for 205:\nExpected: false\nObtained: "+ newList.contains(205));
    }

}
