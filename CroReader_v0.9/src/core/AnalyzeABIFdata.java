package core;

import java.util.*;


/*Method calls for manipulating processed data from channel 9-12
 * Commented methods are unused but may be used in future versions
 */


public class AnalyzeABIFdata {


	final static int overlappingPeak = -99;

	public static int getMaxVal(short[] rawData) {
		int maxVal = 0;
		short data = 0;

		for (int i = 0; i < rawData.length; i++) {
			data = rawData[i];

			if (data > maxVal) {
				maxVal = data;
			}
		}

		// System.out.println("Max Val = " + maxVal);
		return (maxVal);
	}

	public static int getAllMaxVal(short[] rawData, short[] rawData2, short[] rawData3, short[] rawData4) {
		int maxVal = 0;
		short data = 0;

		for (int i = 0; i < rawData.length; i++) {
			data = rawData[i];

			if (data > maxVal) {
				maxVal = data;
			}
		}

		for (int i = 0; i < rawData2.length; i++) {
			data = rawData2[i];

			if (data > maxVal) {
				maxVal = data;
			}
		}

		for (int i = 0; i < rawData3.length; i++) {
			data = rawData3[i];

			if (data > maxVal) {
				maxVal = data;
			}
		}

		for (int i = 0; i < rawData4.length; i++) {
			data = rawData4[i];

			if (data > maxVal) {
				maxVal = data;
			}
		}

		// System.out.println("Max Val = " + maxVal);
		return (maxVal);
	}

	public static short[] getMaxValArray(short[] rawData, short[] rawData2, short[] rawData3, short[] rawData4) {
		//short data, data2, data3, data4;
		short[] datalist = new short[4];
		short[] sil = new short[rawData.length];

		System.out.println("peep");
		for(int i = 0; i< rawData.length; i++){
			short maxVal = 0;
			//data = rawData[i];
			//data2 = rawData2[i];
			//data3 = rawData3[i];
			//data4 = rawData4[i];

			datalist[0] = rawData[i];
			datalist[1] = rawData2[i];
			datalist[2] = rawData3[i];
			datalist[3] = rawData4[i];
			//System.out.println(datalist[0] + " " + datalist[1] + " " + datalist[2] + " " + datalist[3]);
			for(int j = 0; j <  datalist.length ; j++){

				if (datalist[j] > maxVal) {
					maxVal = datalist[j];
				}	
			}
			sil[i] = maxVal; 
			//System.out.println(maxVal);
		}

		//for(int i = 0; i<sil.length; i++){
		//System.out.println(sil[i]);
		//}

		return sil;
	}



	//best method for finding good regions based on quality score, local max peaks difference and peak to valley difference 
	public static TreeMap<Integer, Integer> predictiveAlgorithm(byte[] qual, int qt, int qt5,  ArrayList<Integer> co, int qt2, float[] doList, int qt3, int window){
		// force even windows to increase by 1

		ArrayList<Integer> loc = new ArrayList<Integer>();
		TreeMap<Integer, Integer> regionsMap = new TreeMap<Integer, Integer>();
		if(qt >0 ){
			if (window % 2 == 0) {
				window++;
			}


			int index = window/2;
			int i;
			int j;
			int addup = 0;
			int addco = 0;
			int bOfGR = 0;
			int eOfGR = 0;
			float adddo = 0;
			double average = 0;
			double averageCo = 0;
			double averageDo =0;
			boolean breakout = false;
			boolean found = false;
			boolean changer = false;


			for (i = 0; i< qual.length-1;i++){

				for(j = i; j-i <window;j++){
					try{
						addup += qual[j];
						addco += co.get(j);
						adddo += doList[j];

					}
					catch(IndexOutOfBoundsException except){
						breakout = true;
						break;
					}

				}//end inner for 



				if(!breakout){
					average =  (double) addup/ (double)window;
					averageCo =  (double) addco/ (double)window;
					averageDo = (double)adddo / (double) window;
				}
				else{
					average = (double) addup/ j-i;
					averageCo = (double) addco/ j-i;
					averageDo = (double) adddo / j-1;
				}

				if(!found){
					if (average > qt){
						loc.add(i+1  );
						changer = true;
						bOfGR = i+1;
					}
				}

				if(found){
					if (!(averageCo > qt2 && average > qt5 && averageDo >qt3)){

						loc.add(i+1 );
						found = false;
						eOfGR = i+1;
						regionsMap.put(bOfGR, eOfGR);
					}
					//else{
					//regionsMap.put(bOfGR, qual.length);
					//}
				}

				average = 0;
				addup = 0;
				averageCo = 0;
				addco = 0;
				averageDo = 0;
				adddo = 0;

				//ensure found condition runs on the next iteration and not the same iteration
				if(changer){
					found = true;
					changer = false;

				}

			}

			if(found){
				regionsMap.put(bOfGR, qual.length);
			}

			//String output = "";
			for(int k = 0; k<loc.size(); k++){
				//System.out.println("regions");
				//System.out.println(loc.get(k));
				//output += String.valueOf(loc.get(k)) + "\n";

			}

			return regionsMap;
		}

		else{
			return regionsMap;
		}

	}


	//returns key for the maximum region based on the regions created by the predicter algorithm
	public static int MaxRegionFinder(TreeMap<Integer, Integer> regionsMap){
		int max = 0;
		int maxKey = -101;

		if(!regionsMap.isEmpty()){
			for(Iterator<Integer> iter = regionsMap.keySet().iterator();
					iter.hasNext();){

				int key = (int)iter.next();
				int difference = regionsMap.get(key) -key;
				if(difference>max){
					max = difference;
					maxKey = key;
				}
			}
		}
		return maxKey;
	}


	public static byte[] reverseComplementNucs(byte[] qualScoreNuc){

		byte[] nucs = new byte[qualScoreNuc.length];

		for(int i = 0; i<nucs.length; i++){


			char reverseNuc = 'N';
			if((char)qualScoreNuc[i] == 'A'){
				reverseNuc = 'T';
			}

			else if((char)qualScoreNuc[i] == 'T'){
				reverseNuc = 'A';
			}

			else if((char)qualScoreNuc[i] == 'G'){
				reverseNuc = 'C';
			}

			else if((char)qualScoreNuc[i] == 'C'){
				reverseNuc = 'G';
			}

			else if((char)qualScoreNuc[i] == 'N'){
				reverseNuc = 'N';
			}

			nucs[nucs.length-i-1] = (byte)reverseNuc;


		}

		return nucs;

	}
	
	public static short[] reverseLocations(short[] peakLocations){
		
		//short length = (short) channel1.length;
		
		short[] reverseLocations = new short[peakLocations.length];
		
		int max =0;
		for (int j = 0; j<peakLocations.length;j++){
			if(max<peakLocations[j]){
				max = peakLocations[j];	
			}
		}
		
		for(int i = 0; i< peakLocations.length; i++){
			reverseLocations[reverseLocations.length-i-1]= (short) (max - peakLocations[i]);
			
		}
		
		
		return reverseLocations;
		
		
		
	}
	
	public static byte[] reverseQualScore(byte[] qualScore){
		
		byte[] reverseQualScore = new byte[qualScore.length];
		
		for(int i = 0; i< reverseQualScore.length; i++){
			reverseQualScore[reverseQualScore.length-i-1]= qualScore[i];
			
		}
		
		return reverseQualScore;
		
		
		
	}

	public static String processRegionOutputFasta(TreeMap<Integer, Integer> predictor, byte[] qualScoreNuc, String filename, boolean isReversed){

		String output = "";
		int goodRegionThresh = 75;
		int counter = 1;
		if(!isReversed){
			if(!predictor.isEmpty()){


				for(Iterator<Integer> iter = predictor.keySet().iterator();
						iter.hasNext();){


					int key = (int)iter.next();
					int value = predictor.get(key);

					if (value - key > goodRegionThresh){
						output += ">"+filename + ".r" + String.valueOf(counter) + "\n";
						int j = 0;
						for(int i = key; i< value; i++ ){


							if( j%40 ==0 && j !=0){
								output += "\n"; 
							}

						//	if(j%10 == 0 && j%40 != 0){
							//	output += " ";
							//}


							output += String.valueOf((char)qualScoreNuc[i]);
							j++;
						}
						output+= "\n\n";
						counter++;

					}
				}


				return output;

			}

			else{
				output += "There are no good regions found in this sequence.";
				return output;
			}
		}//end if not reversed
		
		else{//if reversed 
			if(!predictor.isEmpty()){
				
				for(Iterator<Integer> iter = predictor.keySet().iterator();
						iter.hasNext();){


					int key = (int)iter.next();
					int value = predictor.get(key);
					
					int start = (qualScoreNuc.length - value); 
					int end = (qualScoreNuc.length - key); 

					if (value - key > goodRegionThresh){
						output += ">" + filename + ".r" + String.valueOf(counter) + "\n";
						int j = 0;
						for(int i = start; i< end; i++ ){


							if( j%40 ==0 && j !=0){
								output += "\n"; 
							}

							if(j%10 == 0 && j%40 != 0){
								output += " ";
							}


							output += String.valueOf((char)qualScoreNuc[i]);
							j++;
						}
						output+= "\n\n";
						counter++;

					}
				}


				return output;
				
				
			}
			else {
				output+= "There is no good regions found in this sequence.";
				return output;
			}
		}
	}

	
	
	public static String processRegionOutputFastq(TreeMap<Integer, Integer> predictor, byte[] qualScoreNuc, byte[] qualScore, String filename,  boolean isReversed){

		String output = "";
		int goodRegionThresh = 75;
		int counter = 1;
		if(!isReversed){
			if(!predictor.isEmpty()){


				for(Iterator<Integer> iter = predictor.keySet().iterator();
						iter.hasNext();){


					int key = (int)iter.next();
					int value = predictor.get(key);

					if (value - key > goodRegionThresh){
						output += "@"+filename + ".r" + String.valueOf(counter) + "\n";
						for(int i = key; i< value; i++ ){


							output += String.valueOf((char)qualScoreNuc[i]);
						}
						
						output+= "\n";
						
						output += "+"+filename + ".r" + String.valueOf(counter) + "\n";
						for(int i = key; i< value; i++ ){
							int shiftQualScore = qualScore[i]+32;
							char asciiQualScore = (char) shiftQualScore;

							output += asciiQualScore;
								
						}
						output+= "\n";
						output+= "\n\n";
						counter++;

					}
				}


				return output;

			}

			else{
				output += "There are no good regions found in this sequence.";
				return output;
			}
		}//end if not reversed
		
		else{//if reversed 
			if(!predictor.isEmpty()){
				
				for(Iterator<Integer> iter = predictor.keySet().iterator();
						iter.hasNext();){


					int key = (int)iter.next();
					int value = predictor.get(key);
					
					int start = (qualScoreNuc.length - value); 
					int end = (qualScoreNuc.length - key); 

					if (value - key > goodRegionThresh){
						output += "@" +filename + ".r" + String.valueOf(counter) + "\n";
						for(int i = start; i< end; i++ ){
	
							output += String.valueOf((char)qualScoreNuc[i]);
						
						}
						output += "\n";
						output += "+"+filename + ".r" + String.valueOf(counter) + "\n";
						for(int i = start; i< end; i++ ){
							int shiftQualScore = qualScore[i]+32;
							char asciiQualScore = (char) shiftQualScore;

							output += asciiQualScore;
								
						}
						output+= "\n";
						output+= "\n\n";
						counter++;

					}
				}


				return output;
				
				
			}
			else {
				output+= "There is no good regions found in this sequence.";
				return output;
			}
		}
	}

	
	
	
	// Pass processed data and find peaks by looking at neighbors. Points that
	// are greater than the two neighbors to
	// the left and right are considered a peak but must be aleast 20% of the
	// height of the max
	// value from that channel
	public static TreeMap<Integer, Integer> peakFinder(short[] rawData, int maxVal) {
		// System.out.println("Peak map\nPos\tVal");
		int preVal = 0;
		int prePos = 0;
		int prePrePos = 0;
		int prePreVal = 0;
		int prePreVal2 = 0;
		int prePreVal3 = 0;
		// int contender1 = 0;
		// int contender2 = 0;
		int val = 0;
		int pos = 0;

		TreeMap<Integer, Integer> peakPosValMap = new TreeMap<Integer, Integer>();

		for (int i = 0; i < rawData.length; i++) {
			prePreVal3 = prePreVal2;
			prePreVal2 = prePreVal;
			prePreVal = preVal;
			prePrePos = prePos;
			prePos = pos;
			preVal = val;
			val = rawData[i];
			pos = i;

			if (prePreVal > val && prePreVal > preVal && prePreVal > prePreVal2 && prePreVal > prePreVal3) {

				if (preVal > maxVal / 20) {

					peakPosValMap.put(prePrePos, prePreVal);
				}
			}
		}

		Iterator<Integer> it = peakPosValMap.keySet().iterator();

		return peakPosValMap;
	}


	public static short[] valleyFinder(short[] peaklocations, short[] sil) {
		short[] valleyLocations = new short[peaklocations.length-1];
		int ii;
		for(int i = 0; i<peaklocations.length-1; i++){
			ii = i +1;

			short firstPeak = peaklocations[i];
			short secondPeak = peaklocations[ii]; 

			//System.out.println("first peak: " + firstPeak + " second: " + secondPeak);
			//System.out.println("peak height: " + sil[peaklocations[i]] + " second height: " + sil[peaklocations[ii]]);
			short min = firstPeak;
			if(firstPeak != secondPeak){
				for(int k = firstPeak + 1 ; k< secondPeak; k++){
					if(sil[k]<sil[min]){
						min = (short)k;
						//System.out.println("valley: " + max);
					}

				}
			}
			else{
				min = -99;
			}

			//			if(i< 400){
			//System.out.println("first peak: " + firstPeak + " second: " + secondPeak);
			//System.out.println("peak height: " + sil[peaklocations[i]] + " second height: " + sil[peaklocations[ii]]);
			//System.out.println("valley: " + min);

			//		}
			valleyLocations[i] = min;
		}

		//System.out.println("valley length " + valleyLocations.length);

		return valleyLocations;
	}


	//Method: go through sil which is an array of maximum peaks across 4 channels.
	//find all distance from all peaks to local values. distance = d
	//find d/o ration
	//where o = high local peak	

	public static float[] doAlgorithm(short[] peaklocations, short[] valley, short[] sil) {
		float[] dOlist = new float[valley.length];
		TreeMap<Short, Float> dOmap = new TreeMap<Short, Float>();


		for(int i = 0; i <valley.length;i++){

			if(valley[i] != overlappingPeak){
				float dO = (float)((sil[peaklocations[i]] - sil[valley[i]])/(float)sil[peaklocations[i]])*100;  

				dOlist[i] = dO;

				dOmap.put(peaklocations[i], dO);
			}

			else{
				dOlist[i] = 0;
			}
		}


		//				for(Iterator<Short> iter = dOmap.keySet().iterator(); iter.hasNext();){
		//		
		//					short pos = (short)iter.next();
		//					System.out.println("peak: " + pos + "\t" + "do: " +
		//							dOmap.get(pos));
		//				}

		//		System.out.println("peak: " + peaklocations.length);
		//		System.out.println("do: " + dOlist.length);
		//		System.out.println("map: " + dOmap.size());


		return dOlist;
	}



	//Method: go through peakLocations. Compare channel data where ever a peak is called
	//compare and find the 2 highest channels at that point and calculated c/o 
	//where c = highest channel - 2nd highest 
	//o = highest peak
	public static ArrayList<Integer> coAlgorithm(short[] data1, short[] data2, short[] data3, short[] data4, short[] peakLocation ){

		int overall, clear, find;
		int co;
		ArrayList<Integer> coList = new ArrayList<Integer>(); 
		for(int i = 0; i<peakLocation.length;i++){
			int[] compare = new int[4];
			//System.out.println(peakLocation[i]);
			find = peakLocation[i];

			compare[0] = data1[find];
			compare[1] = data2[find];
			compare[2] = data3[find];
			compare[3]= data4[find];

			//System.out.println(compare[0] + " " + compare[1] + " " + compare[2] + " " + compare[3]);
			int[] sortCompare = new int[4];
			sortCompare = bubbleSort(compare);

			clear = sortCompare[3] - sortCompare[2];
			overall = sortCompare[3];

			//System.out.println(clear + " " + overall);
			co =  (int) (((float)clear/(float)overall) * 100); //decimal val

			//System.out.println("co: " + co);
			coList.add(co);
		}
		return coList;

	}


	//	// pass treemap of all channel data. use moving window to find unique peaks
	//	public static TreeMap<Integer, String> allPeakDiscrimination(TreeMap<Integer, String> allPeak,
	//			int discriminationWindow) {
	//
	//		// force even windows to increase by 1
	//		if (discriminationWindow % 2 == 0) {
	//			discriminationWindow++;
	//		}
	//
	//		// window index and counters
	//		int i = 0;
	//		int j = 0;
	//		int k = 0;
	//		int pos = 0;
	//
	//		// set up return tree and the counter
	//		TreeMap<Integer, String> disPeak = new TreeMap<Integer, String>();
	//		Iterator<Integer> it = allPeak.keySet().iterator();
	//
	//		ArrayList<String> values = new ArrayList<String>();
	//		ArrayList<String> nucArray = new ArrayList<String>();
	//		int[] posArray = new int[allPeak.size()];
	//		String[] posValues = new String[allPeak.size()];
	//
	//		i = 0;
	//
	//		// copy input TreeMap into array
	//		while (it.hasNext()) {
	//			pos = it.next();
	//			posArray[i] = pos;
	//			posValues[i] = allPeak.get(pos);
	//			// System.out.println(i+ " " + posValues[i]);
	//			i++;
	//
	//		}
	//
	//		for (int z = 0; z < posValues.length; z++) {
	//			int tempMax = -1000;
	//			String tempNuc = "nuc";
	//			String tempString = posValues[z];
	//			String[] stringSplited = tempString.split(", ");
	//
	//			for (k = 0; k < stringSplited.length; k++) {
	//				String[] stringSplited2 = stringSplited[k].split("\\s+");
	//				// String tempNuc = stringSplited2[0];
	//
	//				if (tempMax < Integer.parseInt(stringSplited2[1])) {
	//					tempMax = Integer.parseInt(stringSplited2[1]);
	//					// System.out.println(Integer.parseInt(stringSplited2[1]));
	//					tempNuc = stringSplited2[0];
	//				}
	//			}
	//			values.add(String.valueOf(tempMax));
	//			nucArray.add(tempNuc);
	//		}
	//
	//		// for (int count = 0; count<values.size(); count++){
	//		// System.out.println("val " + values.get(count));
	//		// }
	//		// for (int count = 0; count<nucArray.size(); count++){
	//		// System.out.println("nuc " + nucArray.get(count));
	//		// }
	//
	//		System.out.println("size val " + values.size());
	//		System.out.println("size nuc " + nucArray.size());
	//
	//		// go thru array
	//		for (i = 0; i < posArray.length; i++) {
	//			String tempNuc = "hi";
	//			j = i;
	//
	//			while (j < posArray.length) {
	//				if (posArray[j] - posArray[i] > discriminationWindow) {
	//					break;
	//				}
	//				// System.out.println("Got here");
	//				// comparison code
	//
	//				int tempMax = -1000;
	//				// System.out.println(Integer.parseInt(values.get(j)));
	//				if (tempMax < Integer.parseInt(values.get(j))) {
	//					tempMax = Integer.parseInt(values.get(j));
	//					tempNuc = nucArray.get(j);
	//				}
	//
	//				if (!disPeak.containsValue(tempNuc + " " + tempMax)) {
	//					disPeak.put(posArray[j], tempNuc + " " + tempMax);
	//				}
	//				j++;
	//			}
	//
	//		}
	//
	//		// for debugging purposes
	//		// for (Iterator<Integer> iter = disPeak.keySet().iterator();
	//		// iter.hasNext();) {
	//		//
	//		// int pos1 = (int) iter.next();
	//		// System.out.println("discrimination peaks: " + pos1 + "\t" +
	//		// disPeak.get(pos1));
	//		// }
	//
	//		System.out.println(disPeak.size());
	//
	//		return disPeak;
	//	}

	// same as 1 but returns without nucleotide attached
	//	public static TreeMap<Integer, Integer> allPeakDiscrimination2(TreeMap<Integer, String> allPeak,
	//			int discriminationWindow) {
	//
	//		// force even windows to increase by 1
	//		if (discriminationWindow % 2 == 0) {
	//			discriminationWindow++;
	//		}
	//
	//		// window index and counters
	//		int i = 0;
	//		int j = 0;
	//		int k = 0;
	//		int pos = 0;
	//		int tempMax = -1000;
	//
	//		// set up return tree and the counter
	//		TreeMap<Integer, Integer> disPeak = new TreeMap<Integer, Integer>();
	//		Iterator<Integer> it = allPeak.keySet().iterator();
	//
	//		ArrayList<String> values = new ArrayList<String>();
	//		ArrayList<String> nucArray = new ArrayList<String>();
	//		int[] posArray = new int[allPeak.size()];
	//		String[] posValues = new String[allPeak.size()];
	//
	//		i = 0;
	//
	//		System.out.println(allPeak.size());
	//
	//		// copy input TreeMap into array
	//		while (it.hasNext()) {
	//			pos = it.next();
	//			posArray[i] = pos;
	//			posValues[i] = allPeak.get(pos);
	//			i++;
	//		}
	//
	//		// for(i = 0 ; i <posArray.length; i++){
	//		// System.out.println(i+ " " + posArray[i]);
	//		// }
	//
	//		for (int z = 0; z < posValues.length; z++) {
	//			tempMax = -1000;
	//			String tempNuc = "nuc";
	//			String tempString = posValues[z];
	//			String[] stringSplited = tempString.split(", ");
	//
	//			for (k = 0; k < stringSplited.length; k++) {
	//				String[] stringSplited2 = stringSplited[k].split("\\s+");
	//				// String tempNuc = stringSplited2[0];
	//
	//				if (tempMax < Integer.parseInt(stringSplited2[1])) {
	//					tempMax = Integer.parseInt(stringSplited2[1]);
	//					// System.out.println(Integer.parseInt(stringSplited2[1]));
	//					tempNuc = stringSplited2[0];
	//				}
	//			}
	//			values.add(String.valueOf(tempMax));
	//			nucArray.add(tempNuc);
	//		}
	//
	//		// go thru array
	//		for (i = 0; i < posArray.length; i++) {
	//			String tempNuc = "hi";
	//			j = i;
	//			tempMax = -1000;
	//			int count = -1;
	//			while (j < posArray.length) {
	//				if (posArray[j] - posArray[i] > discriminationWindow) {
	//					break;
	//				}
	//				// comparison code
	//
	//				if (tempMax < Integer.parseInt(values.get(j))) {
	//					// System.out.println(Integer.parseInt(values.get(j)));
	//					tempMax = Integer.parseInt(values.get(j));
	//					tempNuc = nucArray.get(j);
	//					count++;
	//				}
	//
	//				j++;
	//			}
	//			if (!disPeak.containsValue(tempMax))
	//				disPeak.put(posArray[count + i], tempMax);
	//		}
	//
	//		// for debugging purposes
	//		// for (Iterator<Integer> iter = disPeak.keySet().iterator();
	//		// iter.hasNext();) {
	//		//
	//		// int pos1 = (int) iter.next();
	//		// System.out.println("discrimination peaks: " + pos1 + "\t" +
	//		// disPeak.get(pos1));
	//		// }
	//
	//		System.out.println(disPeak.size());
	//
	//		return disPeak;
	//	}

	// input a treemap<int,int> and compare position peaks to those in original
	// channel data. if any channel is overlapping
	// with treemap peak, it is marked as bad, else it is marked as good.
	//	public static TreeMap<Integer, Integer> Overlapping(TreeMap<Integer, Integer> filteredData, short[] rawData1,
	//			short[] rawData2, short[] rawData3, short[] rawData4) {
	//
	//		int i = 0;
	//		int pos = 0;
	//		int temp1 = 0;
	//		int temp2 = 0;
	//		int temp3 = 0;
	//		int temp4 = 0;
	//		int removeCount = 0;
	//		System.out.println("before overlap: " + filteredData.size());
	//		ArrayList<Integer> valuesRemove = new ArrayList<Integer>();
	//
	//		Iterator<Integer> it = filteredData.keySet().iterator();
	//
	//		while (it.hasNext()) {
	//			pos = it.next();
	//
	//			temp1 = rawData1[pos];
	//			temp2 = rawData2[pos];
	//			temp3 = rawData3[pos];
	//			temp4 = rawData4[pos];
	//
	//			System.out
	//			.println(pos + " " + filteredData.get(pos) + " " + temp1 + " " + temp2 + " " + temp3 + " " + temp4);
	//
	//			if (temp1 == filteredData.get(pos)) {
	//				temp1 = -1000;
	//			}
	//
	//			else if (temp2 == filteredData.get(pos)) {
	//				temp2 = -1000;
	//			} else if (temp2 == filteredData.get(pos)) {
	//				temp2 = -1000;
	//			} else if (temp1 == filteredData.get(pos)) {
	//				temp2 = -1000;
	//			}
	//
	//			if (((temp1 > (int) filteredData.get(pos) * .8)) || (temp2 > (int) filteredData.get(pos) * .8
	//					|| (temp3 > (int) filteredData.get(pos) * .8) || (temp4 > (int) filteredData.get(pos) * .8))) {
	//
	//				valuesRemove.add(pos);
	//				removeCount++;
	//				System.out.println("removed: " + removeCount);
	//
	//			}
	//
	//		}
	//
	//		for (i = 0; i < valuesRemove.size(); i++) {
	//			filteredData.remove(valuesRemove.get(i));
	//		}
	//
	//		System.out.println("after overlap " + filteredData.size());
	//
	//		return filteredData;
	//	}

	//	// same as one, but takes string input and outputs as string
	//	public static TreeMap<Integer, String> OverlappingString(TreeMap<Integer, String> filteredData, short[] rawData1,
	//			short[] rawData2, short[] rawData3, short[] rawData4) {
	//
	//		int i = 0;
	//		int pos = 0;
	//		int temp1 = 0;
	//		int temp2 = 0;
	//		int temp3 = 0;
	//		int temp4 = 0;
	//		int max = -1000;
	//		int goodCount = 0;
	//		int badCount = 0;
	//
	//		ArrayList<String> values = new ArrayList<String>();
	//		ArrayList<String> nucArray = new ArrayList<String>();
	//		int[] posArray = new int[filteredData.size()];
	//		String[] posValues = new String[filteredData.size()];
	//		TreeMap<Integer, String> goodBadData = new TreeMap<Integer, String>();
	//
	//		System.out.println("before overlap: " + filteredData.size());
	//		// ArrayList<Integer> valuesRemove = new ArrayList<Integer>();
	//		Iterator<Integer> it = filteredData.keySet().iterator();
	//
	//		// take note of filtered data values and position
	//		while (it.hasNext()) {
	//			pos = it.next();
	//			posArray[i] = pos;
	//			posValues[i] = filteredData.get(pos);
	//			i++;
	//		}
	//
	//		// if more than one entry in string
	//		for (int z = 0; z < posValues.length; z++) {
	//
	//			String tempNuc = "nuc";
	//			String tempString = posValues[z];
	//			String[] stringSplited = tempString.split(", ");
	//
	//			for (int k = 0; k < stringSplited.length; k++) {
	//				String[] stringSplited2 = stringSplited[k].split("\\s+");
	//
	//				if (max < Integer.parseInt(stringSplited2[1])) {
	//					max = Integer.parseInt(stringSplited2[1]);
	//					tempNuc = stringSplited2[0];
	//				}
	//			}
	//			values.add(String.valueOf(max));
	//			nucArray.add(tempNuc);
	//			max = -1000;
	//		}
	//
	//		for (i = 0; i < posArray.length; i++) {
	//			temp1 = rawData1[posArray[i]];
	//			temp2 = rawData2[posArray[i]];
	//			temp3 = rawData3[posArray[i]];
	//			temp4 = rawData4[posArray[i]];
	//
	//			if (temp1 == Integer.valueOf(values.get(i))) {
	//				temp1 = -1000;
	//			}
	//
	//			else if (temp2 == Integer.valueOf(values.get(i))) {
	//				temp2 = -1000;
	//			} else if (temp3 == Integer.valueOf(values.get(i))) {
	//				temp3 = -1000;
	//			} else if (temp4 == Integer.valueOf(values.get(i))) {
	//				temp4 = -1000;
	//			}
	//
	//			if (((temp1 > (int) Integer.valueOf(values.get(i)) * .8))
	//					|| (temp2 > (int) Integer.valueOf(values.get(i)) * .8
	//							|| (temp3 > Integer.valueOf(values.get(i)) * .8)
	//							|| (temp4 > Integer.valueOf(values.get(i)) * .8))) {
	//
	//				goodBadData.put(posArray[i], nucArray.get(i) + " " + values.get(i) + " " + "bad");
	//				badCount++;
	//
	//			} else {
	//				goodBadData.put(posArray[i], nucArray.get(i) + " " + values.get(i) + " " + "good");
	//				goodCount++;
	//			}
	//
	//		}

	// for debugging purposes
	//for (Iterator<Integer> iter = goodBadData.keySet().iterator(); iter.hasNext();) {

	//int pos1 = (int) iter.next();
	//System.out.println("data: " + pos1 + "\t" + goodBadData.get(pos1));
	//}

	//System.out.println("after overlap " + goodBadData.size());
	//System.out.println(badCount);
	//System.out.println(goodCount);

	//return goodBadData;
	//}


	static int[] bubbleSort(int ar[])
	{
		for (int i = (ar.length - 1); i >= 0; i--)
		{
			for (int j = 1; j <= i; j++)
			{
				if (ar[j-1] > ar[j])
				{
					int temp = ar[j-1];
					ar[j-1] = ar[j];
					ar[j] = temp;
				} 
			} 
		}
		return ar;
	}
}
