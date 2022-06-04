package pdv2;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;

//@SuppressWarnings("serial")
public class Player extends JFrame{
/**
	 * 
	 */
//private static final long serialVersionUID = 1L;
private int width;
private int height;
private Container contentPane;
private JTextArea message;
private JButton b1;
private JButton b2;
private JButton b3;
private int playerID;
private int otherPlayer;
private int []values;
private int maxTurns;
private int turnsMade;
private int myPoints;
private int enemyPoints;
private boolean buttonsEnabled;

private ClientSideConnection csc;

public Player(int w, int h) {
	width = w;
	height = h;
	contentPane = this.getContentPane();
	message = new JTextArea();
	b1=new JButton("0");
	b1=new JButton("1");
	b1=new JButton("2");
	values = new int[3];
	turnsMade = 0;
	myPoints=0;
	enemyPoints = 0;
}
//GUI metodes izveide
public void setUpGUI() {
	this.setSize(width, height);
	this.setTitle("Player No"+playerID);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	contentPane.setLayout(new GridLayout(1,5));
	contentPane.add(message);
	message.setText("Client-server based game");
	message.setWrapStyleWord(true);
	message.setLineWrap(true);
	message.setEditable(false);
	contentPane.add(b1);
	contentPane.add(b2);
	contentPane.add(b3);
	
	if (playerID==1) {
		message.setText("Esi speletajs No1. Tu saks speli pirmais");
		otherPlayer=2;
		buttonsEnabled=true;
	}else {
		message.setText("Tu esi speletajs No2. Gaidi savu gajenu");
		otherPlayer = 1;
		buttonsEnabled =false;
		//updates gajenu metodi
		Thread t = new Thread(new Runnable() {
		public void run() {
			updateTurn();
		}
		});
		t.start();
	}
	toggleButtons();

	this.setVisible(true);
}
//connect to server

public void connectToServer() {
csc = new ClientSideConnection();
}

//veido pogas, anonima klase
public void setUpButtons() {
	ActionListener al = new ActionListener() {
		public void actionPerformed (ActionEvent ae) {
			JButton b = (JButton) ae.getSource();
			int bNum = Integer.parseInt(b.getText());
			message.setText("You clicked button #"+bNum +"Now wait for player#" +otherPlayer);
		turnsMade++;
			System.out.println("Gajenu izdartais skaits:"+turnsMade);
			// pec mana mana gajiena pogas nevar klikskinat
			
			buttonsEnabled=false;
			toggleButtons(); 
			
			//noteiktas pogas vertibas
			myPoints += values[bNum - 1];
			System.out.println("Mani punkti:" + myPoints);
			csc.sendButtonNum(bNum);
			
			//parbauda otra speletaja uzvaras punktus
			if (playerID ==2 && turnsMade == maxTurns) {
				CheckWinner();
			}else{
				buttonsEnabled = true;
				Thread t = new Thread(new Runnable() {
					public void run( ) {
						updateTurn();
					}
				});
				t.start();
			}
			
		}
	};
	b1.addActionListener(al);
	b2.addActionListener(al);
	b3.addActionListener(al);
}
public void toggleButtons() {
	
	b1.setEnabled(buttonsEnabled);
	b2.setEnabled(buttonsEnabled);
	b3.setEnabled(buttonsEnabled);
}


//lient side connection incapsulate networking instructions for the client
private class ClientSideConnection{
	private Socket socket;
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	
	public ClientSideConnection() {
		System.out.println("Klients");
		try {
			socket = new Socket("localhost", 51734);//creates sockets and innitiates the connection to the server
			//data input stream
			dataIn = new DataInputStream(socket.getInputStream());
			dataOut = new DataOutputStream(socket.getOutputStream());
		playerID = dataIn.read();
		System.out.println("Savienots ar serveri, ka Speletajs No" + playerID);
		maxTurns = dataIn.readInt()/2;
		values[0] = dataIn.readInt();
		values[1] = dataIn.readInt();
		values[2] = dataIn.readInt();
		//values[3] = dataIn.readInt();
		System.out.println("Maksimals gajienu skats:"+maxTurns);
		System.out.println("Vertiba No1 ir" + values[0]);
		System.out.println("Vertiba No2 ir" + values[1]);
		System.out.println("Vertiba No3 ir" + values[2]);
		//System.out.println("Vertiba No4 ir" + values[3]);
		} catch (IOException ex) {
			System.out.println("IO Exception from CSC constructor");
		}
	}
	public void sendButtonNum(int n) {
		try {
			dataOut.writeInt(n);
			dataOut.flush();
		}catch (IOException ex) {
			System.out.println("IOException from sendButtonNum()CSC");
		}
	}
	public int receiveButtonNum() {
		int n=-1;
		try {
			n=dataIn.readInt();
			System.out.println("Player No" + otherPlayer + "clicked button No" +n);
		}catch (IOException ex) {
			System.out.println("IOEsception from receiveButtonNum() CSC");
		}
		return n;
	}
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}
}
public void startReceivingButtonNums() {
	Thread t = new Thread(new Runnable() {
		public void run() {
			while(true) {
				csc.receiveButtonNum();
			}
		}
	});
	t.start();
}
public void updateTurn() {
	int n = csc.receiveButtonNum();
	message.setText("Opponents izvelejas ciparu No" + n + ".Tav gajiens");
	enemyPoints += values[n-1];
	System.out.println("Tavam pretiniekam ir"+enemyPoints+"punkti");
	buttonsEnabled=true;
	if (playerID ==1 && turnsMade == maxTurns) {
		CheckWinner();
	}else{
		buttonsEnabled = true;
	}
	toggleButtons();
}

public void CheckWinner() {
	buttonsEnabled = false;
	if(myPoints > enemyPoints) {
		message.setText("Tu UZVAREJI!\n" + "Tavi punkti:"+myPoints+"\n"+"PRETINIEKS"+enemyPoints);
	}else if (myPoints < enemyPoints) {
		message.setText("Tu ZAUDEJI!\n" + "Tavi punkti:"+myPoints+"\n"+"PRETINIEKS"+enemyPoints);
	//}else{
	//	message.setText("Vienads skaits!\n" + "Tavi punkti:"+myPoints+);
	}
	csc.closeConnection();
}
public void closeConnection() {
	try {
		Socket socket = new Socket();
		socket.close();
		System.out.println("Sessija beigusies");
	}catch (IOException ex) {
		System.out.println("IOEsception on closeConnection() CSC");
	}
}
public static void main(String[]args) {
	Player p = new Player(500, 100);
	p.connectToServer();
	p.setUpGUI();
	p.setUpButtons();
}
}

