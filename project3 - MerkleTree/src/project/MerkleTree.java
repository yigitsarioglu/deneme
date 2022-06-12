package project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

import util.HashGeneration;


public class MerkleTree {
	public String path;   //dosya adreslerini içerir,merkle tree oluþturmada gerekli
	public ArrayList <String> adress=new ArrayList();
	public List <String> hash_adress=new ArrayList();
	

	public ArrayList <Stack <String> > corruptChunk=new ArrayList() ;


	public List< List<String> > hashTable=new ArrayList();  //tüm tablo burada
	public int numberOfLeaves;  //en alttaki yaprak sayýsý
	public int level;           //toplam level sayýsý 

	Scanner sc;
	private Node root;
	
	
	public MerkleTree(List < List<String> > tempList) {
		this.hashTable=tempList;
		this.numberOfLeaves=this.hashTable.get(0).size();
		this.level=calculateLevel (this.numberOfLeaves );
		String data =this.hashTable.get(this.hashTable.size()-1).get(0);   //root belirleniyor
		this.hash_adress=this.hashTable.get(0);
		Node newNode=new Node(data);
		this.root=newNode;
		this.buildTree(this.hashTable);
	}

	public MerkleTree(String path) {
		this.path=path;
		this.adress=openReadFile(path);   //dosya adreslerini liste icerisinde tutacak
		generateHashes();                 //dosyalarýn hash kodlarýný oluþturup hash_adres listesinde tutar
		this.numberOfLeaves=this.hash_adress.size();

		this.level=calculateLevel (this.numberOfLeaves );  //level sayýsýný hesaplar

		hashTable.add(hash_adress);  //en alttaki hash adreslerini listeye ekliyoruz
		generateStringHashes();      //sýrayla alttan üste hash kodlarýný listeye yazdýrýr


		String data =this.hashTable.get(this.hashTable.size()-1).get(0);  //root belirleniyor
		Node newNode=new Node(data);
		this.root=newNode;

		this.buildTree(this.hashTable);


	}



	public void buildTree( List< List<String> > hashT) {
		List< List<String> > tempHashTable =hashT;
		List <String> tempList=new ArrayList();
		Queue <Node> qs=new LinkedList();



		qs.add(this.root);
		Node parent;

		for(int i=tempHashTable.size()-2; i>=0; i--) {
			tempList=tempHashTable.get(i);
			int size =tempList.size();

			int indext=0;
			for(int z=0; z< size/2 ;z++) {
				parent=qs.remove();

				if(size>=indext) {
					String value=tempList.get(indext);
					Node newNodes= new Node(value);
					parent.setLeft(newNodes);
					qs.add(parent.getLeft());
					indext=indext+1;

				}
				if(size>=indext) {
					String value2=tempList.get(indext);
					Node newNodes2= new Node(value2);
					parent.setRight(newNodes2);
					qs.add(parent.getRight());
					indext=indext+1;

				}

			}

		}
	}




	public void generateHashes() {
		String s="";
		String temp="";
		try {
			for(int i=0;i<adress.size() ; i++) {
				//String temporary=openFiles(this.adress.get(i));
				// this.file_contents.add(temporary);
				File f=new File(this.adress.get(i));
				String temp_hash= HashGeneration.generateSHA256(f);
				temp=HashGeneration.generateSHA256(s);
				this.hash_adress.add(temp_hash);
				
			}
		}
		catch(Exception e) {
			System.out.println("Exception occured");
		}

	}



	public void generateStringHashes() {
		List <String> tempList=new ArrayList();

		int noleaves=this.numberOfLeaves;
		int levels=this.level;

		try {
			for(int i=0; i< levels ; i++) {
				List <String> newTxList=new ArrayList();	
				tempList=hashTable.get(i);

				for(int index=0; index<tempList.size() ; index++ ) {
					String left=tempList.get(index);
					String right="";
					if(index+1==tempList.size()) {
						right="";
					}
					else {
						right=tempList.get(index+1);
					}

					String s = left.concat(right);
					String sha2HexValue = HashGeneration.generateSHA256(s);
					newTxList.add(sha2HexValue);
					index++;


				}	
				hashTable.add(newTxList);


			}

		}
		catch(Exception e) {
			System.out.println("Exception occured");
		}


	}



	public ArrayList<String> openReadFile(String files) {
		ArrayList <String> tempList=new ArrayList();

		try {
			sc=new Scanner(new File(files));
		}
		catch(Exception e){
			System.out.println("Could not find the file");

		}

		while(sc.hasNextLine()) {
			String temp=sc.nextLine();
			tempList.add(temp);
		}

		return tempList;

	}

	public static double logb( double a, double b ){
		return Math.log(a) / Math.log(b);
	}

	public static double log2( double a ){
		return logb(a,2);
	}

	public static int calculateLevel (int leaves ){
		int tempLevel=(int) log2(leaves);
		double d=log2(leaves);
		double x = d - Math.floor(d);

		if(x>0.0) {
			return tempLevel+1;
		}
		else {
			return tempLevel;
		}
	}



	///////////////////////////////////////////////////////////


	public Node getRoot() {
		return this.root;
	}


	public  List<String> getHashAdress(){
		return this.hash_adress;
	}

////////////////////////////////////
	
	public boolean checkAuthenticity(String string) {
		// TODO Auto-generated method stub
		ArrayList <String> tempList=new ArrayList();
		tempList=openReadFile(string);

		if(tempList.get(0).equals(this.root.getData())) {
			return true;
		}
		else {
			return false;
		}	
	}

	

	public ArrayList<Stack<String>> findCorruptChunks(String string) {

	
		List< List<String> > tempHashTable=new ArrayList();
		tempHashTable=this.buildingFromMeta(string);
		MerkleTree temp=new MerkleTree(tempHashTable);
		///////////////
		Stack <String> myStack=new Stack();
		

		if(this.root.getData().equals(temp.getRoot().getData())) {
			myStack.add(null);
			myStack.add(null);
			myStack.add(null);
			this.corruptChunk.add(myStack);
			this.corruptChunk.add(myStack);
			this.corruptChunk.add(myStack);
			return this.corruptChunk;
		}

		else {
			Node parent=this.root;
			Node current=temp.root;

			while(current.getLeft()!=null && current.getRight()!=null) {
				myStack.push(current.getData());
				if(! current.getLeft().getData().equals(parent.getLeft().getData())) {
					current=current.getLeft();
					parent=parent.getLeft();
					
				}
				if(! current.getRight().getData().equals(parent.getRight().getData() ) ) {
					current=current.getRight();
					parent=parent.getRight();
				}



			}
			myStack.push(current.getData());
			this.corruptChunk.add(myStack);
			return this.corruptChunk;

		}



	}
	
	/////////////////////////////////////////////////////////////
	
	
	//returns hash table
	public  List< List<String> > buildingFromMeta(String string){
		Queue <String> myQueue=new LinkedList();
		ArrayList <String> tempList=new ArrayList();
		tempList=openReadFile(string);


		for(int x=0 ;x<tempList.size(); x++) {
			myQueue.add(tempList.get(x));
		}

		double d= log2(tempList.size());
		double x = d - Math.floor(d);
		int level= (int) Math.floor(d);

		if(x>0.0) {
			level=level+1;
		}

		////////

		List< List<String> > hashingTable=new ArrayList();

		int index=0;
		for(int i=0 ; i<level; i++) {
			index=(int) Math.pow(2, i );
			List <String> newTxList=new ArrayList();

			for(int j=1 ; j<=index ; j++) {
				if(!myQueue.isEmpty()) {
					String temp=myQueue.poll() ;
					newTxList.add(temp);
				}

			}

			hashingTable.add(newTxList);
		}

		///////////////////
		List< List<String> > tempHashTable=new ArrayList();

		for(int i=hashingTable.size()-1 ;i>=0; i--) {

			tempHashTable.add(hashingTable.get(i));
		}
		
		
		return tempHashTable;  //roottan,leaves'e gider
		
	}
	
	
	/**
	 * Builds merkle tree with hash values(from meta file)
	 * @param string file adress
	 * @return bottom hash values of the tree
	 */
	public List <String> buildMerkleTree (String string){
		List< List<String> > tempHashTable=new ArrayList();
		tempHashTable=this.buildingFromMeta(string);
		MerkleTree temp=new MerkleTree(tempHashTable);
		return temp.hash_adress;
	}

}