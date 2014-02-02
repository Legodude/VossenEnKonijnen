package vk.view.graph;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import vk.actor.*;
import vk.model.*;

public class PieGraph extends JPanel{

	/**
	 * KAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAS
	 */
	private static final long serialVersionUID = -4691880041923259628L;
	private final int cirkelSize= 360;
	
	public PieGraph(Graphics gInput) {
		gInput.setColor(Color.WHITE);
		gInput.fillRect(0, 0, this.getWidth(), this.getHeight());

		float rC=FieldStats.getCount(Rabbit.class);
		float fC=FieldStats.getCount(Fox.class);
		float bC=FieldStats.getCount(Alligator.class);
		float hC=FieldStats.getCount(Hunter.class);
		float gC=FieldStats.getCount(Grass.class);
		float zC=FieldStats.getCount(ZombieRabbit.class);

		float total = rC + fC + bC + hC + gC;

		float temp = 0.0f;

		temp = (3.6f * ((rC/total)*100));
		int rA = Math.round(temp);

		temp = (3.6f * ((fC/total)*100));
		int fA = Math.round(temp);

		temp = (3.6f * ((bC/total)*100));
		int bA = Math.round(temp);

		temp = (3.6f * ((hC/total)*100));
		int hA = Math.round(temp);

		temp = (3.6f * ((gC/total)*100));
		int gA = Math.round(temp);

		gInput.setColor(this.model.getColor(Rabbit.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, 0, rA);
		gInput.setColor(this.model.getColor(Fox.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, rA, fA);
		gInput.setColor(this.model.getColor(Bear.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA) , bA);
		gInput.setColor(this.model.getColor(Hunter.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA + bA) , hA);
		gInput.setColor(this.model.getColor(Grass.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA + bA + hA) , gA);
	}
}
