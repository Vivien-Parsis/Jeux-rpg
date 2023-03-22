package game;

import java.util.ArrayList;

public class Merchant extends Person{
    ArrayList<Item> Offer = new ArrayList<Item>();
    public Merchant(String name, ArrayList<Item> Offer)
    {
       super(name,1,0,0);
       this.Offer = Offer;
    }
    public String info()
    {
        String offerString = "\n~~~~~offer~~~~~~\n";
        for(Item item : Offer)
        {
            offerString += item + " -> " + item.getgoldValue() +" gold\n";
        }
        if(offerString.equals("\n~~~~~offer~~~~~~\n"))
        {return "";}
        return offerString.substring(0, offerString.length()-1);
    }

    public void AddOffer(Item item)
    {Offer.add(item);}
    public void RemoveItem(int index)
    {Offer.remove(index);}

    public ArrayList<Item> getOffer()
    {return this.Offer;}
}
