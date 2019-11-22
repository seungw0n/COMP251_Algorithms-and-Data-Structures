/*
 *  NAME : JOENG, SEUNG WON
 *  Student Number :
 *  No Collaborators
 */

public class main {
    public static void main(String[] args) {
    //TODO:build the hash table and insert keys using the insertKeyArray function.

     //

     // List X
     // 1023
     // {70, 54, 19, 58, 46, 14, 67, 80, 3, 93, 47, 50, 74, 72, 85, 95, 86, 91, 81, 90}

     // List Y
     // 590
     // {79, 13, 45, 64, 32, 95, 67, 27, 78, 18, 41, 69, 15, 29, 72, 57, 81, 50, 60, 14}
     int A_x = 1023;
     int A_y = 590;
     int[] x_keys = {70, 54, 19, 58, 46, 14, 67, 80, 3, 93, 47, 50, 74, 72, 85, 95, 86, 91, 81, 90};
     int[] y_keys = {79, 13, 45, 64, 32, 95, 67, 27, 78, 18, 41, 69, 15, 29, 72, 57, 81, 50, 60, 14};


     // Using formula : "2^(w-1) < A < 2^(w)" to get w.
     int w_x = getW(A_x);
     int w_y = getW(A_y);

     Chaining chainMap_x = new Chaining(w_x, 0, A_x);
     Chaining chainMap_y = new Chaining(w_y, 0, A_y);

     int chainCollision_x = chainMap_x.insertKeyArray(x_keys);
     int chainCollision_y = chainMap_y.insertKeyArray(y_keys);
     System.out.println("----------------------------------------------------------------");
     System.out.println("Q1");
     System.out.println("Chaining # Collisions for x: " + chainCollision_x);
     System.out.println("Chaining # Collisions for y: " + chainCollision_y);
     System.out.println("----------------------------------------------------------------");

     Open_Addressing openMap_x = new Open_Addressing(w_x, 0, A_x);
     Open_Addressing openMap_y = new Open_Addressing(w_y, 0, A_y);

     int openCollision_x = openMap_x.insertKeyArray(x_keys);
     int openCollision_y = openMap_y.insertKeyArray(y_keys);

     System.out.println("Q2");
     System.out.println("Chaining # Collisions for x: " + chainCollision_x);
     System.out.println("Open Addressing # Collisions for x: " + openCollision_x );
     System.out.println("----------------------------------------------------------------");
     System.out.println("Q3");
     System.out.println("Chaining # Collisions for y: " + chainCollision_y);
     System.out.println("Open Addressing # Collisions for y: " + openCollision_y);
     System.out.println("----------------------------------------------------------------");


    }

    // Helper method getW : to get W
    public static int getW(int A)
    {
     int W = 0;

     while(A >= 1)
     {
      A = A/2;
      W++;
     }
     return W;
    }


}
