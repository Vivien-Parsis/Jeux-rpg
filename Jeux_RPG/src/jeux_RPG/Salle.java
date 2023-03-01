package jeux_RPG;

public class Salle {
	String RoomName;
	Personnage RoomBose;
	Objet RoomItem;
	Salle NorthRoom;
	Salle SouthRoom;
	Salle EastRoom;
	Salle WestRoom;
	
	public Salle(String RoomName,Personnage RoomBose,Objet RoomItem){
		this.RoomName = RoomName;
		this.RoomBose = RoomBose;
		this.RoomItem = RoomItem;
		this.NorthRoom = null;
		this.SouthRoom = null;
		this.EastRoom = null;
		this.WestRoom = null;
	}
	public void setExit(Salle NorthRoom, Salle SouthRoom, Salle EastRoom, Salle WestRoom)
	{
		this.NorthRoom = NorthRoom;
		this.SouthRoom = SouthRoom;
		this.EastRoom = EastRoom;
		this.WestRoom = WestRoom;
	}
}
