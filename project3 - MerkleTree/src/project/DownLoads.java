package project;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DownLoads extends Thread  {
	public List <List <String> > downloadList=new ArrayList();  // download_from_trusted_source okur
	public List <List <String>> adressValues=new ArrayList();   //indirilecek baðlantýlar
	public List <String> fileAdress=new ArrayList(); 
	String path;

	public DownLoads(String path) {
		this.path=path;
		
		// download();
	}
	
	public void run() {
		openFile(this.path);
		
	}
	
	public void run2(){
		
		download();
	}
	
	public  void  openFile(String path) {


		List <String> tempList=new ArrayList();
		Scanner sc=null;

		try {
			sc=new Scanner(new File(path));
		}
		catch(Exception e){
			System.out.println("Could not find the file");

		}

		while(sc.hasNextLine()) {
			String temp=sc.nextLine();
			if(! temp.equals("")){
				tempList.add(temp);
			}
			else {
				downloadList.add(tempList);
				tempList=new ArrayList();
			}
		}
		downloadList.add(tempList);


		// System.out.println(downloadList);

	}


	public void download() {


		for(int j=0; j<this.downloadList.size();j++) {
			List <String> tempList=new ArrayList();
			List <String> adressList=new ArrayList();
			tempList=this.downloadList.get(j);


			for(int i=0 ;i<tempList.size(); i++) {

				String link=tempList.get(i);

				String last = link.substring(link.lastIndexOf('/') + 1);
				String adress="secondaryPart/data/";
				String new_adress=adress.concat(last);

				adressList.add(new_adress);
				File out=new File(new_adress);
				new Thread(new Downloader(link,out)).start();
			}
			adressValues.add(adressList);

		}


		// System.out.println("adresses " + adressValues);
		// String paths = adressValues.get(0).get(1);
		// System.out.println("path 0 " + paths);


	}



	public void downloadFromSource(String path,String fileAdres) {


		ArrayList <String> readFileList=new ArrayList();  


		readFileList=openReadFile(path);  //4.txt dosyasýnýn içini okur
		// System.out.println(readFileList);

		String url=readFileList.get(0);
		String number=url.substring(url.lastIndexOf('/') -1);
		// String fileAdr=fileAdres + "/0" + number ;
		for(int z=0; z<readFileList.size() ; z++ ) {
			String newLink=readFileList.get(z);

			this.JavaDownloadFileFromURL(newLink,fileAdres);
		}
	}



	public static ArrayList<String> openReadFile(String files) {
		ArrayList <String> tempList=new ArrayList();
		Scanner sc=null;
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


	public  void JavaDownloadFileFromURL(String url, String fileAdres) {
		try {
			String last = url.substring(url.lastIndexOf('/') + 1);
			String adress="secondaryPart/" + fileAdres  ;
			String new_adress=adress.concat(last);
			this.fileAdress.add(new_adress);

			downloadUsingNIO(url, new_adress);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}


	public  void createFile(List<String> lines, String fileName) {

		try {
			Path file = Paths.get(fileName);
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch ( IOException e ) {
			e.printStackTrace();
		}


	}



}