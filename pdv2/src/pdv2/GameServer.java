package pdv2;

import java.util.Arrays;
import java.util.Scanner ;

//Game Server 
import java.io.*;
import java.net.*;
public class GameServer {
//public class GameServer {

	private ServerSocket ss;
	private int numPlayers;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	private int turnsMade;
	private int maxTurns;
	private int[] values;
	private int player1ButtonNum;
	private int player2ButtonNum;
	
	
	public GameServer() {
		System.out.println("Game Server");
		numPlayers =0;
		//dala norada to cik daudz gajienu ir atlauts izdarit speletajiem
		turnsMade = 0;
		maxTurns= 3;
		values = new int[3];
		for (int i = 0; i<values.length; i++) {
			values[i] = (int)Math.ceil(Math.random()*100);
			System.out.println("Value No"+(i+1)+ "is"+ values[i]);
		}
		try {
			ss = new ServerSocket(51734);
		}catch (IOException ex) {
			System.out.println("IOExeption from GameServer Constructor");
		}
		}
	public void acceptConnection() {
		//serveris veido savienojumu
		try {
			System.out.println("Waiting for connection...");
			while (numPlayers <2) {
				Socket s = ss.accept();
				// pec tam kad savienojums pieniemts, pieveino speletajus
				numPlayers++;
				System.out.println("Player #" + numPlayers+"has connected");
				ServerSideConnection ssc = new ServerSideConnection(s, numPlayers); //socket savienojums, un pieskir speletajiem id
			if (numPlayers == 1) {
				player1 = ssc;
			}else {
				player2=ssc;
			}
			Thread t = new Thread(ssc);// will run in a new thread
			t.start();
			}
			System.out.println("Otrajs speletaijs pievienojas spelei");
		} catch (IOException ex) {
			System.out.println("IOException from acceptConnection");
		}
	}
	//one thread for each palyer
	private class ServerSideConnection implements Runnable {
		
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket s, int id) {
			socket = s;
			playerID = id;	
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
			} catch (IOException ex) {
				System.out.println("IOExeption from SSC constructor");
			}
		}
		//run method of the clientserver connection class
		public void run() {
			try {
				dataOut.writeInt(playerID);
				dataOut.writeInt(maxTurns);
				dataOut.writeInt(values[0]);
				dataOut.writeInt(values[1]);
				dataOut.writeInt(values[2]);
				//dataOut.writeInt(values[3]);
				dataOut.flush();
				
				while (true) {
					if (playerID==1) {
						player1ButtonNum=dataIn.readInt();
						System.out.print("Speletajs 1 izvelejas ciparu" + player1ButtonNum);
					player2.sendButtonNum(player1ButtonNum);
					} else {
						player2ButtonNum=dataIn.readInt();
						System.out.print("Speletajs 2 izvelejas ciparu" + player2ButtonNum);
						player1.sendButtonNum(player2ButtonNum);
					}
					turnsMade++;
					if(turnsMade == maxTurns) {
						System.out.println("Gajienu maksimalaijs skaits ir sasniegts");
					break;
					}
				}
				player1.closeConnection();
				player2.closeConnection();
				
			} catch (IOException ex) {
				System.out.println("IOExeption from run() ssc");
				}
			}
		
		private void closeConnection() {
			// TODO Auto-generated method stub
			
		}
		public void sendButtonNum(int n) {
			try {
				dataOut.writeInt(n);
				dataOut.flush();
				
			}catch (IOException ex) {
				System.out.println("IOException from sendButtonNum() ssc");
			}
		}
		}
	private void closeConnection() {
		try {
			Socket socket = new Socket();
			socket.close();
			System.out.println("Savienojums slegts");
		}catch (IOException ex) {
			System.out.println("IOEsception on closeConnection() SCS");
		}
	}
	
//public class game {

	
//speletāju metode
	private static int addUser() {
		  //String sa[] = { "player1", "palyer2" };
		 // while (true) {
		 Scanner sc = new Scanner(System.in) ;
			  System.out.println("Cik speletāji spēle?");
			
			  final int player = sc.nextInt();
		 // }
		//creates an array in the memory of length 10  
		  int[] array = new int[2];  
		  System.out.println("ievadi daudzumu: ");  
		  for(int i=0; i<player; i++)  
		  {  
		  //reading array elements from the user   
		  array[i]=sc.nextInt();  
		  }  
		  System.out.println("Array elements are: ");  
		  // accessing array elements using the for loop  
		  for (int i=0; i<player; i++)   
		  {  
		  System.out.println(array[i]);  
		 
	}
		return player;
	}
	    Scanner sc = new Scanner(System.in) ;

/*	    int totalCards = getStartCount(sc);
//1. uzstadit carsu daudzumu
	    private static int getStartCount(Scanner sc) {
	        while(true) {
	        	System.out.println("Spele Skaitlis");
	            System.out.println("Izvelies skaitli  "
	                    + "(no 6 lidz 36)") ;
	            int totalCards = sc.nextInt() ;
	            if (totalCards >= 6 && totalCards <= 36) {
	                return totalCards;
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
	    }*/
	    //4.pamata metode
	    public static void main(String[] args) {
	    	//public static void main(String[]args) {
	    		//GameServer gs = new GameServer();
	    	GameServer gs = new GameServer();
	    		gs.acceptConnection();
	    	}
	    }

	       /* try (Scanner sc = new Scanner(System.in) ;) {

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
	}*/