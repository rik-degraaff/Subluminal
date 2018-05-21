package tech.subluminal.shared.util.poissonDiskSampler;

/**
 * Class suitable for representing mappings from discrete 2D space to [0, 1]
 */
public abstract class RealFunction2D
{
	public abstract double getDouble(int x, int y);
}
