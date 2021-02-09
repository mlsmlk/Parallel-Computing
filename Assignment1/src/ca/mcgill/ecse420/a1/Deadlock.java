public class Deadlock {


   // public static Object resourceA = new Object();
   // public static Object resourceB = new Object();
    String resourceA;
    String resourceB;

    public static void main(String[] args) {
        Deadlock ds = new Deadlock();

        ds.readData();
        ds.writeData();
    }


        public void writeData(){

            synchronized (resourceA) {
                //obtained lock on A

                synchronized (resourceB) {
                    //obtained lock on B
                    //DO SOME THING
                    System.out.println("Hello");
                }
            }
        }

        public void readData(){
            synchronized (resourceB) {
                //obtained lock on B
                synchronized (resourceA) {
                    //obtained lock on A
                    //DO SOME THIS
                    System.out.println("Bonjour");
                }
            }
        }
    }