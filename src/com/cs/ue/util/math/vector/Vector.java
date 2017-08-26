package com.cs.ue.util.math.vector;

public class Vector
{
	private float i, j, k;
	
	public Vector()
	{
		setI(0);
		setJ(0);
		setK(0);
	}
	
	public Vector(float i, float j, float k)
	{
		setI(i);
		setJ(j);
		setK(k);
	}

	public float getI() {
		return i;
	}

	public void setI(float i) {
		this.i = i;
	}

	public float getJ() {
		return j;
	}

	public void setJ(float j) {
		this.j = j;
	}

	public float getK() {
		return k;
	}

	public void setK(float k) {
		this.k = k;
	}
}
