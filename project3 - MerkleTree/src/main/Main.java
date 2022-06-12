package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import project.DownLoads;
import project.Downloader;
import project.MerkleTree;
/**
 * ONEMLI NOT : Once file indirme iþlemini ve dosyalarý oluþturmasý gerekiyor.Bunun için thread(5000 ms) koydum..
 * Bunu koymaz isem iki kere run etmek gerekir..
 * @author yiðit
 *
 */
public class Main  {

	public static void main(String[] args){


		MerkleTree m0 = new MerkleTree("sample/white_walker.txt");		
		String hash = m0.getRoot().getLeft().getRight().getData();
		System.out.println(hash);

		boolean valid = m0.checkAuthenticity("sample/white_walkermeta.txt");
		System.out.println(valid);

		// The following just is an example for you to see the usage. 
		// Although there is none in reality, assume that there are two corrupt chunks in this example.
		
		ArrayList<Stack<String>> corrupts = m0.findCorruptChunks("sample/white_walkermeta.txt");
		
		System.out.println("Corrupt hash of first corrupt chunk is: " + corrupts.get(0).pop());
		System.out.println("Corrupt hash of second corrupt chunk is: " + corrupts.get(1).pop());
		
		
		new File("secondaryPart/data/split").mkdirs();
		download("secondaryPart/data/download_from_trusted.txt");

	}



	public static void download(String path) {
		//if you want to download  another folder,you could change data_adress variable
		String data_adres="data/split/";
		
		//Downloads class start to download all the system
		DownLoads mx=new DownLoads(path);
		mx.run();
        try {
        	Thread.sleep(5000);
        } catch (Exception e) {}
        mx.run2();
        try {
        	Thread.sleep(5000);
        } catch (Exception e) {}
		
		
		
		// Folder oluþturma
		List <String> tempLists=new ArrayList();
		for(int m=0 ; m< mx.adressValues.size() ; m++ ) {
			tempLists=mx.adressValues.get(m);
			String paths=tempLists.get(1);   //4.txt

			char adres_number=paths.charAt( paths.lastIndexOf('/') + 1);
			String folder_name="secondaryPart/data/split/0" + adres_number;  
			new File(folder_name).mkdirs();
			
		}
		
		
		
		List <String> tempList=new ArrayList();

		for(int x=0 ; x < mx.adressValues.size() ;  x++ ) {
			
			//Takes the source from the adress values
			tempList=mx.adressValues.get(x);    

			//In the list: first is main source,second is another source
			String paths=tempList.get(1);   //4.txt

			char adres_number=paths.charAt( paths.lastIndexOf('/') + 1);

			


			String adress=data_adres + "0" + adres_number +"/";
			mx.downloadFromSource(paths,adress);      // burasý deðiþecek

			
			//File yaratýlacak, sources'lar burada tutulacak
			String fileName="secondaryPart/data/split/filename" + adres_number +".txt";
			mx.createFile(mx.fileAdress,fileName);

			MerkleTree m0 = new MerkleTree(fileName);

			/////

			String meta_adress="secondaryPart/data/" + adres_number +"meta.txt";   // burasý deðiþecek
			boolean valid = m0.checkAuthenticity(meta_adress);
			System.out.println("checking validity of number " + adres_number + " is " + valid);


			if(valid) {
				System.out.println("ok");
				// mx.downloadFromSource(paths,"data/split");
				mx.fileAdress.clear();

			}

			else {
				List <String> hashes= m0.buildMerkleTree(meta_adress); // meta adressteki leaves hash'ler
				List <Integer> corupt_hashes=new ArrayList();

				for(int i=0 ; i< hashes.size() ; i++ ) {
					if(! m0.hash_adress.get(i).equals(hashes.get(i))  ) {
						corupt_hashes.add(i);

					}

				}

				String alt_path=tempList.get(2);
				List <String> alternative=new ArrayList();
				alternative= DownLoads.openReadFile(alt_path);



				for(int j=0 ;j< corupt_hashes.size() ; j++) {

					int number=corupt_hashes.get(j);

					String adres= alternative.get(number);

					String file_path=adress;                         //burasý deðiþecek
					mx.JavaDownloadFileFromURL(adres , file_path);

				}

				System.out.println("invalid : corupt hashes " + corupt_hashes);


				MerkleTree m1 = new MerkleTree(fileName);

				boolean validity = m1.checkAuthenticity(meta_adress);


				System.out.println("Second checking validity of number " + adres_number + " is " + validity);
				mx.fileAdress.clear();
			}

		}	


	}



}
