package model;

import java.io.Serializable;

public class FileCursor implements Serializable, Comparable<FileCursor> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tid;
	private int index;
	public FileCursor()
	{
		this.tid = 0;
		this.index = 0;
	}
	public FileCursor(String tid2, String index2) {
		// TODO Auto-generated constructor stub
		this.tid = Integer.parseInt(tid2);
		this.index = Integer.parseInt(index2);
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		try{	
			if(this.tid==0||this.tid<tid)
				this.tid = tid;
	
		}catch(Exception ex)
		{
			this.tid = tid;
		}
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		try{	
			if(this.index==0||this.index<index)
				this.index = index;
	
		}catch(Exception ex)
		{
			this.index = index;
		}
	}
	@Override
	public int compareTo(FileCursor o) {
		// TODO Auto-generated method stub
		try{
			if(this.getTid()<o.getTid()) return -1;
			else if(this.getTid()>o.getTid()) return 1;
			else if(this.getTid()==o.getTid())
			{
				if(this.getIndex()<o.getIndex()) return -1;
				else if(this.getIndex()>o.getIndex()) return 1;
				else if(this.getIndex()==o.getIndex()) return 0;
			}
		}catch(NullPointerException ex)
		{
			System.out.println("Null object");
		}
		return 0;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("{TID,Index} = {"+this.tid+","+this.index+"}");
		return Integer.toString(tid)+"@"+Integer.toString(index);
	}
	
}
