package dev;

public class DataHandle {
	private int id;
	private int xStart;
	private int xEnd;
	private double rCover;
	private double rCon;
	private int width;
	private int aver;
	public DataHandle() {
		this(0, 0, 0, 0, 0, 0, 0);
	}
	
	public DataHandle(int id, int xStart, int xEnd, double rCover, double rCon, int width, int aver) {
		this.id = id;
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.rCover = rCover;
		this.rCon = rCon;
		this.width = width;
		this.aver = aver;
	}

	public int getxStart() {
		return xStart;
	}
	public int getxEnd() {
		return xEnd;
	}
	public double getrCover() {
		return rCover;
	}
	public double getrCon() {
		return rCon;
	}
	public int getWidth() {
		return width;
	}
	public int getAver() {
		return aver;
	}
	public void setxStart(int xStart) {
		this.xStart = xStart;
	}
	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}
	public void setrCover(double rCover) {
		this.rCover = rCover;
	}
	public void setrCon(double rCon) {
		this.rCon = rCon;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setAver(int aver) {
		this.aver = aver;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + ";" + xStart + ";" + xEnd + ";" + rCover + ";" + rCon + ";" + width + ";" + aver;
	}

	

}
