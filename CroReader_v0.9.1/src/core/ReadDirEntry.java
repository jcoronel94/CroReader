package core;

import java.io.IOException;
import java.io.RandomAccessFile;


//saves the file info and prints to console 
public class ReadDirEntry
{
	public static AbifDirEntryBean read(RandomAccessFile file)
	{
		AbifDirEntryBean dirEntry = new AbifDirEntryBean();
		StringBuilder sb = new StringBuilder();
		
		try
		{
			sb.append((char)file.readByte());
			sb.append((char)file.readByte());
			sb.append((char)file.readByte());
			sb.append((char)file.readByte());
			
			dirEntry.setName(sb.toString());
			dirEntry.setNumber(file.readInt());
			dirEntry.setElementType(file.readShort());
			dirEntry.setElementSize(file.readShort());
			dirEntry.setNumberOfElements(file.readInt());
			dirEntry.setDataSize(file.readInt());
			dirEntry.setDataOffset(file.readInt());
			dirEntry.setDataHandler(file.readInt());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		//uncomment for console information
//		System.out.println("DirEntry content:\n" +
//						   "Name = " + dirEntry.getName()  + "\n" +
//						   "Number = " + dirEntry.getNumber() + "\n" +
//						   "ElementType = " + dirEntry.getElementType() + "\n" +
//						   "ElementSize = " + dirEntry.getElementSize() + "\n" +
//						   "Number Of Elements = " + dirEntry.getNumberOfElements() + "\n" +
//						   "DataSize = " + dirEntry.getDataSize() + "\n" +
//						   "DataOffset = " + dirEntry.getDataOffset() + "\n" +
//						   "DataHandler = " + dirEntry.getDataHandler());
		
		return(dirEntry);
	}
}
