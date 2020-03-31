package simpledb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Tuple maintains information about the contents of a tuple. Tuples have a
 * specified schema specified by a TupleDesc object and contain Field objects
 * with the data for each field.
 */
public class Tuple implements Serializable {

    private static final long serialVersionUID = 1L;

    private TupleDesc tDesc;
    private RecordId Recordid;
    private ArrayList<Field> fieldAr;
    /**
     * Create a new tuple with the specified schema (type).
     * 
     * @param td
     *            the schema of this tuple. It must be a valid TupleDesc
     *            instance with at least one field.
     */
    public Tuple(TupleDesc td) {
    	tDesc = td;
    	fieldAr = new ArrayList<Field>();
    }

    /**
     * @return The TupleDesc representing the schema of this tuple.
     */
    public TupleDesc getTupleDesc() {
        return tDesc;
    }

    /**
     * @return The RecordId representing the location of this tuple on disk. May
     *         be null.
     */
    public RecordId getRecordId() {
        return Recordid;
    }

    /**
     * Set the RecordId information for this tuple.
     * 
     * @param rid
     *            the new RecordId for this tuple.
     */
    public void setRecordId(RecordId rid) {
        Recordid = rid;
    }

    /**
     * Change the value of the ith field of this tuple.
     * 
     * @param i
     *            index of the field to change. It must be a valid index.
     * @param f
     *            new value for the field.
     */
    public void setField(int i, Field f) {
    	try {
    		if (i == fieldAr.size())
    			fieldAr.add(f);
    		else
    			fieldAr.set(i, f);
    	}
    	catch (IndexOutOfBoundsException e) {
    		return;
    	}
    }

    /**
     * @return the value of the ith field, or null if it has not been set.
     * 
     * @param i
     *            field index to return. Must be a valid index.
     */
    public Field getField(int i) {
    	try {
    		Field fieldRet = fieldAr.get(i);
    		return fieldRet;
    	}
    	catch (IndexOutOfBoundsException e) {
    		return null;
    	}
    }

    /**
     * Returns the contents of this Tuple as a string. Note that to pass the
     * system tests, the format needs to be as follows:
     * 
     * column1\tcolumn2\tcolumn3\t...\tcolumnN\n
     * 
     * where \t is any whitespace, except newline, and \n is a newline
     */
    public String toString() {
    	String str = new String();
        for (int i = 0; i < fieldAr.size(); ++i) {
        	str += fieldAr.get(i).toString();
        	if (i == fieldAr.size() - 1)
        		str += "\n";
        	else
        		str += "\t";
        }
        return str;
    }
    
    /**
     * @return
     *        An iterator which iterates over all the fields of this tuple
     * */
    public Iterator<Field> fields() {
        return fieldAr.iterator();
    }
    
    /**
     * Reset the TupleDesc of this tuple
     * Does not need to worry about the fields inside the Tuple
     * */
    public void resetTupleDesc(TupleDesc td) {
        tDesc = td;
    }
}
