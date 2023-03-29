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
        {offerString += item + " -> " + item.getgoldValue() +" gold\n";}
        if(offerString.equals("\n~~~~~offer~~~~~~\n"))
        {return "";}
        return offerString.substring(0, offerString.length()-1);
    }

    public void AddaOffer(Item item)
    {Offer.add(item);}
    public void RemoveaOffer(int index)
    {Offer.remove(index);}
    public String save()
    {
        String offer="|";
        if(this.Offer.size()>0)
        {
            for(int i = 0; i<this.Offer.size();i++)
            {
                offer+=this.Offer.get(i).save()+"|";
            }
            offer = offer.substring(0,offer.length()-1);
        }
        else
        {offer+="empty";}
        return super.save()+offer;
    }
    public ArrayList<Item> getOffer()
    {return this.Offer;}
}
