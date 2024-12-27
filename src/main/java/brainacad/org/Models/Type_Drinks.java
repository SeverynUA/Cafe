package brainacad.org.Models;

public enum Type_Drinks
{
    Hot_drink("Hot drink"),
    Soft_drink("Soft drink"),
    Alcohol_drink("Alcohol drink");

    private final String type;

    Type_Drinks(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}

