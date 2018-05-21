package tech.subluminal.shared.util.poissonDiskSampler;

/**
 * Class suitable for representing mappings from continuous 2D space [0, 1]*[0, 1] to [0, 1].
 */
public abstract class RealFunction2DDouble
{
	public abstract double getDouble(double x, double y);
}
