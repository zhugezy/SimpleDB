package simpledb;

import java.io.*;
import java.util.*;

/**
 * HeapFile is an implementation of a DbFile that stores a collection of tuples
 * in no particular order. Tuples are stored on pages, each of which is a fixed
 * size, and the file is simply a collection of those pages. HeapFile works
 * closely with HeapPage. The format of HeapPages is described in the HeapPage
 * constructor.
 * 
 * @see simpledb.HeapPage#HeapPage
 * @author Sam Madden
 */
public class HeapFile implements DbFile {

	File file;
	TupleDesc tDesc;
    /**
     * Constructs a heap file backed by the specified file.
     * 
     * @param f
     *            the file that stores the on-disk backing store for this heap
     *            file.
     */
    public HeapFile(File f, TupleDesc td) {
        file = f;
        tDesc = td;
    }

    /**
     * Returns the File backing this HeapFile on disk.
     * 
     * @return the File backing this HeapFile on disk.
     */
    public File getFile() {
        return file;
    }

    /**
     * Returns an ID uniquely identifying this HeapFile. Implementation note:
     * you will need to generate this tableid somewhere ensure that each
     * HeapFile has a "unique id," and that you always return the same value for
     * a particular HeapFile. We suggest hashing the absolute file name of the
     * file underlying the heapfile, i.e. f.getAbsoluteFile().hashCode().
     * 
     * @return an ID uniquely identifying this HeapFile.
     */
    public int getId() {
    	return file.getAbsoluteFile().hashCode();
    }

    /**
     * Returns the TupleDesc of the table stored in this DbFile.
     * 
     * @return TupleDesc of this DbFile.
     */
    public TupleDesc getTupleDesc() {
        return tDesc;
    }

    // see DbFile.java for javadocs
    public Page readPage(PageId pid) {
        byte[] data = new byte[BufferPool.PAGE_SIZE];
        try {
			RandomAccessFile f = new RandomAccessFile(getFile(), "r");
			f.seek(pid.pageNumber()*BufferPool.PAGE_SIZE);
			f.read(data, 0, BufferPool.PAGE_SIZE);
			f.close();
			return new HeapPage((HeapPageId)pid, data);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
    }

    // see DbFile.java for javadocs
    public void writePage(Page page) throws IOException {
        // some code goes here
        // not necessary for lab1
    }

    /**
     * Returns the number of pages in this HeapFile.
     */
    public int numPages() {
        return (int)file.length() / BufferPool.PAGE_SIZE;
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> insertTuple(TransactionId tid, Tuple t)
            throws DbException, IOException, TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    // see DbFile.java for javadocs
    public ArrayList<Page> deleteTuple(TransactionId tid, Tuple t) throws DbException,
            TransactionAbortedException {
        // some code goes here
        return null;
        // not necessary for lab1
    }

    class HeapFileIterator implements DbFileIterator {
    	TransactionId tid;
    	Iterator<Tuple> curPageIter;
    	int pid;
    	public HeapFileIterator(TransactionId tid) {
    		pid = 0;
    		this.tid = tid;
    		curPageIter = null;
    	}
    	public void open() {
    		pid = 0;
    		try {
				curPageIter = ((HeapPage)(
						Database.getBufferPool().getPage(tid, new HeapPageId(getId(), 0), Permissions.READ_ONLY)
						)
					).iterator();
				
			} catch (TransactionAbortedException e) {
				return;
			} catch (DbException e) {
				return;
			}

    	}
    	
    	public Tuple next() throws TransactionAbortedException, DbException {
    		try {
    			if (curPageIter == null)
    				throw new NoSuchElementException();
    			if (!hasNext())
    				throw new NoSuchElementException();
	    		if (curPageIter.hasNext())
	    			return curPageIter.next();
	    		else {
	    			pid++;
						curPageIter = ((HeapPage)(
								Database.getBufferPool().getPage(tid, new HeapPageId(getId(), pid), Permissions.READ_ONLY)
								)
							).iterator();
					return curPageIter.next();
	    		}
			}
    		catch (TransactionAbortedException e) {
    			throw e;
			} catch (DbException e) {
				throw e;
			}
    	}
    	
    	public boolean hasNext() {
    		if (curPageIter == null || pid >= HeapFile.this.numPages()) {
    			return false;
    		}
    		else if (pid == HeapFile.this.numPages() - 1 && !curPageIter.hasNext()) {
    			return false;
    		}
    		else {
    			return true;
    		}
    	}
    	
    	public void rewind() {
    		open();
    	}
    	
    	public void close() {
    		pid = 0;
    		curPageIter = null;
    	}
    	
    }
    // see DbFile.java for javadocs
    public DbFileIterator iterator(TransactionId tid) {
        return new HeapFileIterator(tid);
    }

}

