package pdv2;


import java.util.Arrays;
import java.util.Scanner ;


public class game {

//public static void main(String[] args) {

    Scanner sc = new Scanner(System.in) ;

int totalMatches = getStartCount(sc);
private static int getStartCount(Scanner sc) {
    while(true) {
        System.out.println("How many matches do you want to play with? "
                + "(from 6 to 60)") ;
        int totalMatches = sc.nextInt() ;
        if (totalMatches >= 6 && totalMatches <= 60) {
            return totalMatches;
        }
    }
}

	    //2.karsu skaittais kava un kombinacijas izvele
	    private static int[] computeAllow(int totalCards, int previous) {
	        if (totalCards == 1) {
	            return new int[]{1};
	        }
	        if (totalCards == 2 && previous < 3 && previous > 0) {
	            return previous == 1 ? new int[]{2} : new int[]{1};
	        }
	        switch (previous) {
	            case 0:
	                return new int[]{1,2,3};
	            case 1:
	                return new int[]{2,3};
	            case 2:
	                return new int[]{1,3};
	            case 3:
	                return new int[]{1,2};

	        }
	        throw new IllegalStateException("Neprecizs iepreikšējais skaitlis" + previous);
	    }
	    //3.speletaju gajiens
	    private static int getPlayerPick(Scanner sc, int totalCards, int player, int previous) {
	        if (totalCards == 1) {
	            System.out.println("TPalikusi viens kartis "
	                    + "kava") ;
	        } else {
	            System.out.println("Kopa " + totalCards
	                    + " kartis kava") ;
	        }

	        final int[] allow = computeAllow(totalCards, previous); 

	        while (true) {

	            System.out.printf("Player %d: Cik daudzu kartis grib paņemt? %s",
	                    player, Arrays.toString(allow));

	            final int cardsThisTurn = sc.nextInt();
	            for (int a : allow) {
	                if (a == cardsThisTurn) {
	                    // Valid input.
	                    return cardsThisTurn;
	                }
	            }

	            System.out.printf("Nepreiza ievāde: ir palicis %d kartis, "
	                + "pedejais speletajs izvelejas %d, "
	                + "tad vari panemt tikai vienu %s\n",
	                    totalCards, previous, Arrays.toString(allow));
	        }
	    }
	    //4.pamata metode
	    public static void main(String[] args) {
	    	
	     try (Scanner sc = new Scanner(System.in) ;) {

	            int totalCards = getStartCount(sc);

	            int cardsPreviousTurn = 0 ;
	            int round = 0 ;
	            int player = 0 ;

	            do {

	                //previousPlayer = player ;
	                player = 1 + (round % 2);
	                round++ ;

	                int cardsThisTurn = getPlayerPick(sc, totalCards, player, cardsPreviousTurn);

	                totalCards -= cardsThisTurn ;
	                cardsPreviousTurn = cardsThisTurn ;

	            } while (totalCards > 1 || totalCards == 1 && cardsPreviousTurn != 1);

	            System.out.println("*** Spele beidzas! Player " + player
	                    + " uzvareja! ***") ;
	        }
	    }
	}