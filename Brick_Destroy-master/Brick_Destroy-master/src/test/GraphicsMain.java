package test;

import java.awt.*;


public class GraphicsMain {

    public static void main(String[] args){
    	Resources.generationResources();
        EventQueue.invokeLater(() -> new GameFrame().initialize());
    }

}
