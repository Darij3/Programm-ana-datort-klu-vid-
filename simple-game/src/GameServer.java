import java.io.*;
import java.net.*;

public class GameServer {

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
	

	public static void main(String[]args) {
		GameServer gs = new GameServer();
		gs.acceptConnection();
	}
}

