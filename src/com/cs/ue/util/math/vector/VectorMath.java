package com.cs.ue.util.math.vector;

public class VectorMath
{	
	public static final float magnitudeVector(Vector v)
	{
		return (float) Math.sqrt(Math.pow(v.getI(), 2) + Math.pow(v.getJ(), 2) + Math.pow(v.getK(), 2));
	}
	
	public static final float dotVector(Vector a, Vector b)
	{
		return (a.getI() * b.getI()) + (a.getJ() * b.getJ()) + (a.getK() * b.getK());
	}
	
	public static final Vector crossVector(Vector a, Vector b)
	{
		Vector resultant = new Vector();
		
		resultant.setI(0);
		resultant.setJ(0);
		resultant.setK((a.getI() * b.getJ()) - (b.getI() * a.getJ()));
		
		return resultant;
	}
	
	public static final Vector unitVector(Vector v)
	{
		Vector u = new Vector();
		u.setI(v.getI() / magnitudeVector(v));
		u.setJ(v.getJ() / magnitudeVector(v));
		u.setK(v.getK() / magnitudeVector(v));
		
		return u;
	}
}
