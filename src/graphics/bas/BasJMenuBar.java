package graphics.bas;

import java.awt.Color;

import javax.swing.JMenuBar;
import javax.swing.border.EmptyBorder;

public class BasJMenuBar extends JMenuBar{
	public BasJMenuBar()
	{
		setBackground(new Color(123,123,123));
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
}
