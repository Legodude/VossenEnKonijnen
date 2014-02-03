package vk.view.graph;

import java.awt.Color;
import java.awt.Graphics;

import vk.actor.*;
import vk.model.*;
import vk.simulation.Simulator;
import vk.view.*;

public class PieGraph extends AbstractView{

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -4691880041923259628L;
	private final int cirkelSize= 360;

	public PieGraph(Simulator newSimulator)
	{		
		super(newSimulator);
	}
	public void drawPie(Graphics gInput)
	{
		gInput.setColor(Color.WHITE);
		gInput.fillRect(125, 125, this.getWidth(), this.getHeight());

		float rC=FieldStats.getCount(Rabbit.class);
		float fC=FieldStats.getCount(Fox.class);
		float bC=FieldStats.getCount(Alligator.class);
		float hC=FieldStats.getCount(Hunter.class);
		float gC=FieldStats.getCount(Grass.class);
		float zC=FieldStats.getCount(ZombieRabbit.class);

		float total = rC + fC + bC + hC + gC + zC;

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
		
		temp = (3.6f * ((zC/total)*100));
		int zA = Math.round(temp);
		
		gInput.setColor(SimView.getColor(Rabbit.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, 0, rA);
		gInput.setColor(SimView.getColor(Fox.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, rA, fA);
		gInput.setColor(SimView.getColor(Alligator.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA) , bA);
		gInput.setColor(SimView.getColor(Hunter.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA + bA) , hA);
		gInput.setColor(SimView.getColor(Grass.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA + bA + hA) , gA);
		gInput.setColor(SimView.getColor(ZombieRabbit.class));
		gInput.fillArc((this.getWidth()-this.cirkelSize)/2, (this.getHeight()-this.cirkelSize)/2, this.cirkelSize, this.cirkelSize, (fA + rA + bA + hA + gA) , zA);
	}
}