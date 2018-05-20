package ch.jeda.project;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class Board {
 
	private View view;
	 
	private Canvas background;
	 
	private MainClient main;
        
        int width = 1600;
        int height = 900;
        
        public Board(MainClient main)
        {
            this.main = main;
            view = new View(width,height);
            background = view.getBackground();
        }
	 
	public void draw() {
	 
            background.drawPolyline(0,height/6,width,height/6);
            for (int i=0; i<14; i++)
            {
                background.drawPolyline((i+1)*width/16,0,(i+1)*width/16,height/6);
            }
	}
	 
}
 
