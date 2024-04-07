package dk.cphbusiness.vp.f2024.Eksamen.textio;

import java.util.Scanner;

public class SystemTextIOImpl extends AbstractTextIO
{
	private static Scanner scanner = new Scanner(System.in);

	@Override
	public void put(String str)
	{
		System.out.print(str);
	}

	@Override
	public void clear()
	{
		for(int i = 0; i < 100; ++i)
		{
			System.out.println();
		}
	}

	@Override
	public String get()
	{
		return scanner.nextLine();
	}
}
