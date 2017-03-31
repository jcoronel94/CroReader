package core;

import java.io.*;
import java.util.*;


//reads file header 
public class ABIFbinaryFileLoader
{
	private static String abifFileFormat = "ABIF";
	private static int abifMajorVersion = 1;
	
	public static ArrayList<AbifDirEntryBean> read(String inputFileLocation)
	{
		RandomAccessFile file = null;
		StringBuilder sb = new StringBuilder();
		int tempInt = 0;
		int offset = 0;
		int numberOfElements = 0;
		AbifDirEntryBean tempDirEntry = null;
		ArrayList<AbifDirEntryBean> dirEntryList = new ArrayList<AbifDirEntryBean>();
		
		try
		{
			try
			{
				file = new RandomAccessFile(inputFileLocation, "r");
				sb.append((char)file.readByte());
				sb.append((char)file.readByte());
				sb.append((char)file.readByte());
				sb.append((char)file.readByte());
				;
				
				if(abifFileFormat.matches(sb.toString()))
				{
					//System.out.println(sb.toString());
				}
				else
				{
					System.err.println("Non-ABIF Format");
					System.exit(-100);
				}
				
				tempInt = file.readShort();
				
				if(tempInt%100 == abifMajorVersion)
				{
					//System.out.println("Version = " + tempInt);
				}
				else
				{
					System.err.println("Unsupported Version");
					System.exit(-100);
				}
				
				AbifDirEntryBean dirEntry = ReadDirEntry.read(file);
				
				numberOfElements = dirEntry.getNumberOfElements();
				offset = dirEntry.getDataOffset();
				
				for(int i = 0 ; i < numberOfElements ; i ++)
				{
					tempDirEntry = ReadDirEntry.read(file);
					dirEntryList.add(tempDirEntry);
					offset = offset +28;
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
		
		return(dirEntryList);
	}
}
