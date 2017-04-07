package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormatSymbols;
import java.text.Normalizer;

public class FindDataInOffsetField {


	public static char[] findOffsetAsChar(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){


		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		char[] charData = new char[numberOfElements];

		if(numberOfElements > 4)
		{
			System.err.println("numberOfElements > 4");
			System.exit(-1);
			//char[] chardata = {'N'};
		}

		else if(numberOfElements == 4){
			int temp = offset;
			//System.out.println(temp);
			//System.out.println(Integer.toBinaryString(temp));
			int bitmask = 0xFF;
			int char4 = bitmask & temp;
			charData[3] = (char) char4;
			//System.out.println(Integer.toBinaryString(char4));
			temp = temp >>> 8;
			//System.out.println(Integer.toBinaryString(temp));
			int char3 = bitmask & temp;
			charData[2] = (char) char3;
			//System.out.println(Integer.toBinaryString(char3));
			temp = temp >>> 8;
			//System.out.println(Integer.toBinaryString(temp));
			int char2 = bitmask & temp;
			charData[1] = (char) char2;
			//System.out.println(Integer.toBinaryString(char2));
			temp = temp >>> 8;
			//System.out.println(Integer.toBinaryString(temp));
			int char1 = bitmask & temp;
			charData[0] = (char) char1;
			//System.out.println(Integer.toBinaryString(char1));
			//char[] chardata = {(char)char1,(char)char2,(char)char3,(char)char4};
			//System.out.println("Results = " + (char)char1 + " " + (char)char2 + " " + (char)char3 + " " + (char)char4);
		}

		else if(numberOfElements == 1){
			int temp = offset; 
			int bitmask = 0xFF;
			temp = temp >>>8;
			charData[0] = (char)temp;
			//char[] chardata = {(char)temp};

		}


		return charData;

	}




	public static String findOffsetAsString(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){


		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		char[] charData = new char[numberOfElements];


		int temp = offset;
		int bitmask = 0xFF;
		for(int i = charData.length-1; i >= 0; i--){
			int charTemp = bitmask & temp;
			//char test = (char)charTemp;
			if(!Character.isIdentifierIgnorable(charTemp)){ //get rid of unprintables 
				charData[i] = (char) charTemp;
				temp = temp >>> 8;
			}
			else{
				charData[i] = ' ';
				temp = temp >>>8;
			}
		

		}

		String output = "";
		for (char s : charData)
		{
			output += s; ///data collection version number
		}

		
		output = output.replaceAll("\\s+",""); //remove replacemnet spaces 
		

		return output;

	}

	public static String findOffsetofTime(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		if(numberOfElements > 1)
		{
			System.err.println("numberOfElements > 1");
			System.exit(-1);
		}



		int temp = offset;
		int bitmask = 0xFF;
		int millisecond = bitmask & temp; //not used, usually kept at 0
		temp = temp >>> 8;
		int second = bitmask & temp;
		temp = temp >>> 8; 
		int minute = bitmask & temp;
		temp = temp >>> 8; 
		int hour = bitmask & temp;

		String time =  hour + ":" + minute + ":" + second;

		return time;

	}

	public static String findOffsetofDate(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		if(numberOfElements > 1)
		{
			System.err.println("numberOfElements > 1");
			System.exit(-1);
		}



		int temp = offset;
		int bitmask = 0xFF;
		int day = bitmask & temp;
		temp = temp >>> 8;
		int month = bitmask & temp;
		int year = temp >> 8; //remainder of the offset is the year 
		String nMonth = new DateFormatSymbols().getMonths()[month-1];	



		String date =  nMonth + " " + day + " " + year;

		return date;

	}



	public static short findOffsetShort(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{

			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		if(numberOfElements > 1)
		{
			System.err.println("numberOfElements > 1");
			System.exit(-1);
		}



		int temp = offset;
		temp = temp >> 16;




		short output = (short)temp;

		return output;

	}



	public static float findOffsetAsFloat(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{

			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		if(numberOfElements > 1)
		{
			System.err.println("numberOfElements > 1");
			System.exit(-1);
		}



		//int temp = intBitsToFloat(offset);
		//int bitmask = 0xFF;

		//temp = temp >>> 32;
		//temp = bitmask & temp;



		//float output = (float)temp;
		float output = Float.intBitsToFloat(specificDirEntry.getDataOffset());

		return output;

	}


	public static short findOffsetCString(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		System.out.println("Looking for " + name + " number = " + number);
		AbifDirEntryBean specificDirEntry = null;
		boolean found = false;

		for(int i = 0 ; i < dirEntryList.size() ; i ++)
		{
			specificDirEntry = dirEntryList.get(i);

			if(specificDirEntry.getName() == null || specificDirEntry.getName().length() == 0)
			{
				continue;
			}



			if((specificDirEntry.getName().matches(name) && specificDirEntry.getNumber() == number))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{

			System.err.println("Unknown Name");
			System.exit(-100);
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		if(numberOfElements > 1)
		{
			System.err.println("numberOfElements > 1");
			System.exit(-1);
		}



		int temp = offset;
		temp = temp << 16;
		int bitmask = 0xFF;
		int day = bitmask & temp;
		temp = temp >> 8;
		int month = bitmask & temp;
		int year = temp >> 8; //remainder of the offset is the year 




		short output = (short)day;

		return output;

	}

}
