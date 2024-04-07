package dk.cphbusiness.vp.f2024.Eksamen.textio;

import dk.cphbusiness.vp.f2024.Eksamen.intf.Location;
import dk.cphbusiness.vp.f2024.Eksamen.intf.Portal;

import java.util.List;

public interface TextIO
{
	void put(String str);
	void clear();
	String get();
	int getInt();
	int getInt(int min, int max);
	Location choose(String title, List<Portal> options, String question);
	int choose(String title, String[] options, String question);
}
