package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable{

    /**
     * A help class to facilitate organizing the information of each field
     * */
	
	ArrayList<TDItem> tditemAr = new ArrayList<TDItem>();
	
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;
        
        /**
         * The name of the field
         * */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }
        
        
        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }
    
    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
    	return tditemAr.iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
    	int Len = typeAr.length;
        for (int i = 0; i < Len; ++i) {
        	tditemAr.add(new TDItem(typeAr[i], fieldAr[i]));
        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        int Len = typeAr.length;
        for (int i = 0; i < Len; ++i) {
        	tditemAr.add(new TDItem(typeAr[i], ""));
        }
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        return tditemAr.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        try {
        	TDItem item = tditemAr.get(i);
        	return new String(item.fieldName);
        }
        catch (NoSuchElementException ex) {
        	throw ex;
        }
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
    	try {
        	TDItem item = tditemAr.get(i);
        	return item.fieldType;
        }
        catch (NoSuchElementException ex) {
        	throw ex;
        }
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
    	int Len = tditemAr.size();
        for (int i = 0; i < Len; ++i) {
        	if (tditemAr.get(i).fieldName.equals(name)) {
        		return i;
        	}
        }
        throw new NoSuchElementException();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        int Arsize = 0;
        for (TDItem it: tditemAr) {
        	Arsize += it.fieldType.getLen();
        }
        return Arsize;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
    	TupleDesc tDesc_ret = new TupleDesc(new Type[0]);
    	for (TDItem item: td1.tditemAr) {
    		tDesc_ret.tditemAr.add(new TDItem(item.fieldType, item.fieldName));
    	}
    	for (TDItem item: td2.tditemAr) {
    		tDesc_ret.tditemAr.add(new TDItem(item.fieldType, item.fieldName));
    	}
        return tDesc_ret;
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
    	
        if (!(o instanceof TupleDesc))
        	return false;
        TupleDesc tDesc = (TupleDesc)o;
        if (tDesc.tditemAr.size() != tditemAr.size())
        	return false;
        for (int i = 0; i < tditemAr.size(); ++i) {
        	if (tDesc.getFieldType(i) != this.getFieldType(i))
        		return false;
        }
        return true;
    }

    public int hashCode() {
        return tditemAr.hashCode();
        //throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldName[0](fieldType[0]), ..., fieldName[M](fieldType[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
        String str_ret = new String();
        for (TDItem it: tditemAr) {
        	str_ret += it.toString() + ",";
        }
        return str_ret.substring(0, str_ret.length() - 1);
    }
}
