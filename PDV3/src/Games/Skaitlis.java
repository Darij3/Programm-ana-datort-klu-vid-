package Games;

import java.util.Scanner;



public class Skaitlis {
	    private int numberToGuess;
	    private Scanner in = new Scanner(System.in);
	    Scanner keyboard = new Scanner(System.in);
	 
	    private int askUserForNumber() {
	        return in.nextInt();
	    }
	   //1 funkcija random math
	    private void generateRandomNumberToGuess() {
	        numberToGuess = (int) (Math.random() * 100) + 1;
	    }
	   
	//2.sasveicinasanas funkcija
	    private void intro() {
	        System.out.println("Spele 'Skaitlis'");
	    }
	/*  //3. speletaju pieveinosanas funkcija
	  		private void addPlayer() {
	  			int      playerCt = 0;  // At the start, there are no players.
	  			// TODO Auto-generated method stub
	  			player[] playerList = new player[3];  // max 2 speletaji
	  		    
	  		    playerList[playerCt] = newPlayer; // jauns speletajs
	              //     briva vieta
	  		    playerCt++;  // speletaju count.
	  		}*/

	  		//4.how much player play
	  		
	  		private void askPlayerNr(){
	  		  System.out.println("cik speletaji speles") ;

	  		  int x = 2;
	  		while (x <= 3)
	        {
	            System.out.println("Speles 2 speletaji");
	        x++;
	            	
	        }
	  		}

	   //4.pamata funkcija ar do while and while cikliem
	    private void askLoop() {
	  
	       Scanner sc = new Scanner(System.in) ;

	       int totalCards ;
	       do {
	           System.out.println("Cik daudz kartis gribi panemt?"
	           + "no 6 lidz 21 ") ;
	           totalCards = sc.nextInt() ;
	       }
	       while (totalCards < 6 || totalCards > 21) ;
	       int matchesThisTurn ;
	       int matchesPreviousTurn = 0 ;
	       int round = 0 ;
	       int player = 0 ;
	       int previousPlayer ;

	       while (true) {

	           round++ ;
	           previousPlayer = player ;
	           player = round % 2 ;
	           if (player == 0) {
	               player = 2 ;
	           }

	           while (true) {

	               if (totalCards == 1) {
	                   System.out.println("Ir palicis viens kartis "
	                   + "kava") ;
	               } else {
	                   System.out.println("Kopa " + totalCards
	                   + " kartis kava") ;
	               }

	               if (round == 1 || round == 2) {
	                   System.out.println("Speletajs " + player + ": Cik daudz"
	                   + "vel vajag kartis(1, 2 or 3)") ;
	               } else {
	                   System.out.println("Speletajs " + player + ": kartis gribi "
	                   + "vel vajag kartis") ;
	               }

	               matchesThisTurn = sc.nextInt() ;

	               if (matchesThisTurn < 1 || matchesThisTurn > 3) {
	                   System.out.println("Nepareiza izvele: vajag izvelet 1,2  "
	                   + "vai 3 karti") ;
	                   continue ;
	               }
	               if (matchesThisTurn == matchesPreviousTurn) {
	                   System.out.println("Tu nevari izvelet vairak par 1 "
	                   + "matches as Player " + previousPlayer) ;
	                   continue ;
	               }
	               if (totalCards == 1 && matchesThisTurn > totalCards) {
	                   System.out.println("Nevari panemt " + matchesThisTurn
	                   + " palicis viena 1 kartis") ;
	                   continue ;
	               }
	               if (matchesThisTurn > totalCards) {
	                   System.out.println("You cannot pick " + matchesThisTurn
	                   + " matches: there are only " + totalCards
	                   + " matches left") ;
	                   continue ;
	               }
	               break ;
	           }

	           totalCards -= matchesThisTurn ;
	           matchesPreviousTurn = matchesThisTurn ;

	           if (totalCards == 0) {
	               System.out.println("Vairak kartis nav!") ;
	               break ;
	           }
	           if (totalCards == 1 && matchesThisTurn == 1) {
	               System.out.println(" Ir palicis viens cartis "
	               + "speletajs " + player + " jau panema ") ;
	               break ;
	           }
	       }
	      //System.out.println(" Spele beidzas! Uzvareja Speletajs " + player    + " !!! ") ;
	        }
	  //5.resultata funkcija
	    private void showResult() {
	    	int player = 0;
			// TODO Auto-generated method stub
			System.out.println("*** The game is over! Player " + player  + " Wins! ***") ;
		}

	    public static void main(String[] args) {
	        Skaitlis game = new Skaitlis();
	        game.generateRandomNumberToGuess();
	        game.intro();
	        //game.addPlayer();
	        game.askLoop();
	        game.showResult();
	        
	    }
		public Game createGame(Skaitlis gameFactory) {
			// TODO Auto-generated method stub
			return null;
		}
	}
