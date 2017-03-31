package core;

//used for saving abif elements
public class AbifDirEntryBean
{
	private String name = null;
	private int number = 0;
	private int elementType = 0;
	private int elementSize = 0;
	private int numberOfElements = 0;
	private int dataSize = 0;
	private int dataOffset = 0;
	private int dataHandler = 0;
	
	public AbifDirEntryBean()
	{
		
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public int getNumber()
	{
		return number;
	}

	/**
	 * @param elementType the elementType to set
	 */
	public void setElementType(int elementType)
	{
		this.elementType = elementType;
	}

	/**
	 * @return the elementType
	 */
	public int getElementType()
	{
		return elementType;
	}

	/**
	 * @param elementSize the elementSize to set
	 */
	public void setElementSize(int elementSize)
	{
		this.elementSize = elementSize;
	}

	/**
	 * @return the elementSize
	 */
	public int getElementSize()
	{
		return elementSize;
	}

	/**
	 * @param numberOfElements the numberOfElements to set
	 */
	public void setNumberOfElements(int numberOfElements)
	{
		this.numberOfElements = numberOfElements;
	}

	/**
	 * @return the numberOfElements
	 */
	public int getNumberOfElements()
	{
		return numberOfElements;
	}

	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(int dataSize)
	{
		this.dataSize = dataSize;
	}

	/**
	 * @return the dataSize
	 */
	public int getDataSize()
	{
		return dataSize;
	}

	/**
	 * @param dataOffset the dataOffset to set
	 */
	public void setDataOffset(int dataOffset)
	{
		this.dataOffset = dataOffset;
	}

	/**
	 * @return the dataOffset
	 */
	public int getDataOffset()
	{
		return dataOffset;
	}

	/**
	 * @param dataHandler the dataHandler to set
	 */
	public void setDataHandler(int dataHandler)
	{
		this.dataHandler = dataHandler;
	}

	/**
	 * @return the dataHandler
	 */
	public int getDataHandler()
	{
		return dataHandler;
	}

}
