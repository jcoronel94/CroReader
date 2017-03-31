package core;

import java.io.RandomAccessFile;
import java.util.ArrayList;


//Class to for extrating data by tag name and number from the ABIF directory Struct

public class LoadDataBySpecificNameAndNumber
{
	public static short[] loadShortData(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number)
	{
		//System.out.println("Looking for " + name + " number = " + number);
		RandomAccessFile file = null;
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
		//System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		short[] dataList = new short[numberOfElements];
		short data = 0;

		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");

				file.seek(offset);

				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					data = file.readShort();
					//System.out.println(i + "\t" + data);
					dataList[i] = data;
					offset = offset + 2;
					file.seek(offset);
				}

			}
			finally
			{
				if(file != null)
				{
					file.close();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		return(dataList);
	}

	
	
	public static long loadLongData(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number)
	{
		System.out.println("Looking for " + name + " number = " + number);
		RandomAccessFile file = null;
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
		//short[] dataList = new short[numberOfElements];
		long data = 0;

		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");

				file.seek(offset);

				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					data = file.readLong();
					//System.out.println(i + "\t" + data);
					//dataList[i] = data;
					offset = offset + 2;
					file.seek(offset);
				}

			}
			finally
			{
				if(file != null)
				{
					file.close();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		return(data);
	}
	

	
	public static String loadPStringData(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number)
	{
		System.out.println("Looking for " + name + " number = " + number);
		RandomAccessFile file = null;
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
			//System.err.println("Unknown Name");
			//System.exit(-100);
			
			return "N/A";
		}

		int offset = specificDirEntry.getDataOffset();
		int numberOfElements = specificDirEntry.getNumberOfElements();
		System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		byte[] dataList = new byte[numberOfElements];
		byte data = 0;

		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");
				//System.out.println(file.length());
				file.seek(offset);

				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					//System.out.print(file.readByte());
					data = file.readByte();

					//System.out.println(i + "\t" + data);

					dataList[i] = data;
					offset = offset + 1;
					file.seek(offset);
				}

			}
			finally
			{
				if(file != null)
				{
					file.close();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		String nData = "";
		for(int i = 1; i < dataList.length; i++){
			nData += (char)dataList[i];
		}

		return(nData);
	}
	
	
	
	public static String loadCStringData(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number)
	{
		System.out.println("Looking for " + name + " number = " + number);
		RandomAccessFile file = null;
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
		byte[] dataList = new byte[numberOfElements];
		byte data = 0;

		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");
				//System.out.println(file.length());
				file.seek(offset);

				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					//System.out.print(file.readByte());
					data = file.readByte();

					//System.out.println(i + "\t" + data);

					dataList[i] = data;
					offset = offset + 1;
					file.seek(offset);
				}

			}
			finally
			{
				if(file != null)
				{
					file.close();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		String nData = "";
		for(int i = 0; i < dataList.length-1; i++){
			nData += (char)dataList[i];
		}

		return(nData);
	}
	
	
	
	
	public static byte[] loadByteData(ArrayList<AbifDirEntryBean> dirEntryList, String inputFileLocation, String name, int number){

		//System.out.println("Looking for " + name + " number = " + number);
		RandomAccessFile file = null;
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
		//System.out.println("Offset = " + offset + " Number of elements = " + numberOfElements);
		byte[] dataList = new byte[numberOfElements];
		byte data = 0;


		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");
				//System.out.println(file.length());
				file.seek(offset);

				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					//System.out.print(file.readByte());
					data = file.readByte();

					//System.out.println(i + "\t" + data);

					dataList[i] = data;
					offset = offset + 1;
					file.seek(offset);
				}

			}
			finally
			{
				if(file != null)
				{
					file.close();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		return(dataList);


	}
	


}
