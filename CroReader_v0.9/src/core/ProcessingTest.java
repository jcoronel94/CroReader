

/*
 * test class not used in actual application
 */


//package core;
//
//import java.awt.Dimension;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TreeMap;
//
//import javax.swing.JFileChooser;
//
//public class ProcessingTest {
//	static Dimension fileChooserDim = new Dimension(700, 600);
//	public static String inputFsaFileLocation = "/Users/armasia/ABIFfileTest.d/LZ103_11_G11_Feb-8-2012-A.fsa";
//
//	public static void main(String[] args) throws IOException{
//		//		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
//		//		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		//		fileChooser.setPreferredSize(fileChooserDim);
//		//		int returnVal = fileChooser.showOpenDialog(null);
//		//
//		//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//		//			inputFsaFileLocation = fileChooser.getSelectedFile().getAbsolutePath();
//
//		//inputFsaFileLocation= "C:\\Users\\jcoro\\Desktop\\sampleData.d\\";
//		// load key dirEntry data
//
//		String output ="";
//		int upperT = 35;
//		int lowerT = 20;
//		int upperCo = 50;
//		int lowerCo = 20;
//		int upperDo = 50;
//		int lowerDo = 20;
//		int qualityExit = 20;
//		int range = upperT - lowerT;
//		int coRange = upperCo-lowerCo;
//		int nextWindow = 1;
//		//int t1 = 52;
//		int window = 5;
//
//		//try all threshholds from lower  to upper by nextWindow
//		int numOfWindows = range/nextWindow;
//
//		//create lists to hold all file info instead of storing to memory and overwriting
//		ArrayList<short[]> channel1List = new ArrayList<short[]>();
//		ArrayList<short[]> channel2List = new ArrayList<short[]>();
//		ArrayList<short[]> channel3List = new ArrayList<short[]>();
//		ArrayList<short[]> channel4List = new ArrayList<short[]>();
//		ArrayList<Integer> firstList = new ArrayList<Integer>();
//		ArrayList<Integer> lastList = new ArrayList<Integer>();
//		ArrayList<short[]> peakLocationList = new ArrayList<short[]>();
//		ArrayList<byte[]> qualityList = new ArrayList<byte[]>();
//		ArrayList<ArrayList<Integer>> listOfCoLists = new ArrayList<ArrayList<Integer>>();
//		ArrayList<short[]> silList = new ArrayList<short[]>();
//		ArrayList<short[]> valleyList = new ArrayList<short[]>();
//		ArrayList<float[]> doList = new ArrayList<float[]>();
//
//		String region = "";
//
//		for(int i = 0; i<8;i++){
//
//
//			//if(i ==0){
//				//inputFsaFileLocation = "C:\\Users\\jcoro\\Desktop\\sampleData.d\\G09.ab1";
//			//}
//			//else{
//				inputFsaFileLocation = String.format("C:\\Users\\jcoro\\Desktop\\sampleData.d\\G0%d.ab1", i+1);
//			//}
//			region = String.format("C:\\Users\\jcoro\\OneDrive\\regions\\region%d.txt", i+1);
//
//			boolean flag = true; 
//			String strLine = "";
//			int first = 0;
//			int last = 0;
//			FileInputStream fstream = new FileInputStream(region);
//			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//			while ((strLine = br.readLine()) != null)   {
//				if(flag){
//					first = Integer.valueOf(strLine);
//					flag = false;
//				}
//
//				last = Integer.valueOf(strLine);
//
//			}
//			firstList.add(first);
//			lastList.add(last);
//
//			ArrayList<AbifDirEntryBean> dirEntryList = ABIFbinaryFileLoader.read(inputFsaFileLocation);
//			// save all channel data
//			short[] channel1Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
//					"DATA", 9);
//			channel1List.add(channel1Data);
//			short[] channel2Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
//					"DATA", 10);
//			channel2List.add(channel2Data);
//
//			short[] channel3Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
//					"DATA", 11);
//
//			channel3List.add(channel3Data);
//			short[] channel4Data = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
//					"DATA", 12);
//			channel4List.add(channel4Data);
//			short[] peakLocation = LoadDataBySpecificNameAndNumber.loadShortData(dirEntryList, inputFsaFileLocation,
//					"PLOC", 2);
//			peakLocationList.add(peakLocation);
//			ArrayList<Integer> coList = AnalyzeABIFdata.coAlgorithm(channel1Data, channel2Data, channel3Data, channel4Data, peakLocation);
//			listOfCoLists.add(coList);
//			byte[] qualScore = LoadDataBySpecificNameAndNumber.loadByteData(dirEntryList, inputFsaFileLocation,
//					"PCON", 2);
//			qualityList.add(qualScore);
//
//			short[] sil= AnalyzeABIFdata.getMaxValArray(channel1Data, channel2Data, channel3Data, channel4Data);
//			silList.add(sil);
//			short[] valley = AnalyzeABIFdata.valleyFinder(peakLocation, sil);
//			valleyList.add(valley);
//			float[] doAlgo = AnalyzeABIFdata.doAlgorithm(peakLocation, valley, sil);
//			doList.add(doAlgo);
//		}
//
//
//		for(int z = lowerT; z<= upperT; z = z+ nextWindow){
//			for(int j = lowerCo; j<= upperCo; j = j+ nextWindow){
//				for(int y = lowerDo; y<= upperDo; y = y+ nextWindow){
//
//					ArrayList<Integer> far = new ArrayList<Integer>();
//					for(int i = 0; i< channel1List.size(); i++){
//
//						TreeMap<Integer, Integer> predict = AnalyzeABIFdata.predictiveAlgorithm(qualityList.get(i), z, qualityExit, listOfCoLists.get(i), j, doList.get(i), y, window );
//						int size = predict.size();
//						int maxKey = AnalyzeABIFdata.MaxRegionFinder(predict);
//						int fdiff,ldiff; 
//						float startAve = 0;
//						float endAve = 0;
//
//						int end = predict.get(maxKey);
//
//
//						//System.out.println(first + " " + predict.get(0));
//						fdiff = firstList.get(i) - maxKey;
//						ldiff = lastList.get(i) - end;
//
//						far.add(fdiff);
//						far.add(ldiff);
//						if(i ==7){
//							output += "Window: " + String.valueOf(window) + " | t1: " + String.valueOf(z) + " | t2: "+ qualityExit + " | CO: " + String.valueOf(j) + " | D0: "+ String.valueOf(y) + " | Value: ";
//							System.out.print("Window: " + window + " | t1: " + z + " | t2: "+ qualityExit + " | C0: " + j + "|D0: " + y + "| Value: ");
//							for(int k = 0; k <far.size(); k++){
//								output += String.valueOf(far.get(k)) + " ";
//								if(k %2 == 0){
//									startAve += Math.abs(far.get(k));
//								}
//
//								else if(k%2 ==1){
//									endAve += Math.abs(far.get(k));
//								}
//
//
//								System.out.print(far.get(k) + " " );
//
//							}//end value printer
//							startAve = (float) (startAve/(far.size()/2.0));
//							endAve = (float) (endAve/(far.size()/2.0));
//							output += " | Start Average: " + String.valueOf(startAve) + " | End Average: " + String.valueOf(endAve) ;
//							output += "\n";
//							System.out.print(" | Start Average: " + String.valueOf(startAve) + " | End Average: " + String.valueOf(endAve));
//							System.out.println("");
//						}
//					}//end 8 for 
//				}
//
//			}//end co for
//		}//end qual for
//
//
//		BufferedWriter bw = null;
//		FileWriter fw = null;
//
//		try {
//
//
//
//			fw = new FileWriter("C:\\Users\\jcoro\\OneDrive\\averages-t1-t2-co-do-info.txt");
//			bw = new BufferedWriter(fw);
//			bw.write(output);
//
//			System.out.println("Done");
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//
//			try {
//
//				if (bw != null)
//					bw.close();
//
//				if (fw != null)
//					fw.close();
//
//			} catch (IOException ex) {
//
//				ex.printStackTrace();
//
//			}
//
//		}
//
//
//
//
//
//
//	}//end of main
//}//end of class
//
//
