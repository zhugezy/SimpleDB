package simpledb;

import java.io.Serializable;
/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.
 */
public class RecordId implements Serializable {

    private static final long serialVersionUID = 1L;
    private PageId Pageid;
    private int iTuplenum;

    /**
     * Creates a new RecordId referring to the specified PageId and tuple
     * number.
     * 
     * @param pid
     *            the pageid of the page on which the tuple resides
     * @param tupleno
     *            the tuple number within the page.
     */
    public RecordId(PageId pid, int tupleno) {
        Pageid = pid;
        iTuplenum = tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int tupleno() {
        return iTuplenum;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        return Pageid;
    }

    /**
     * Two RecordId objects are considered equal if they represent the same
     * tuple.
     * 
     * @return True if this and o represent the same tuple
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
        	return true;
        else if (o instanceof RecordId) {
        	RecordId recordid2 = (RecordId) o;
        	return Pageid.equals(recordid2.getPageId()) && iTuplenum == recordid2.tupleno();
        }
        else
        	return false;
    }

    /**
     * You should implement the hashCode() so that two equal RecordId instances
     * (with respect to equals()) have the same hashCode().
     * 
     * @return An int that is the same for equal RecordId objects.
     */
    @Override
    public int hashCode() {
        return 255 * Pageid.hashCode() + iTuplenum;
    }

}
