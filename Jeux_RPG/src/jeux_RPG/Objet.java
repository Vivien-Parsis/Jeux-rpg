package jeux_RPG;

public class Objet {
	String nameItem;
	int weight;
	
	public Objet(String nameItem, int weight)
	{
		this.nameItem = nameItem;
		this.weight = weight;
	}
	public Objet() {
		this("testitem",0);
	}
}
