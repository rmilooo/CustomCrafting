package de.drache.customCrafting.Classes;

public enum CraftingTypes {
    HELMET("###", "#-#", "---"),
    CHESTPLATE("#-#", "###", "###"),
    LEGGINGS("###", "#-#", "#-#"),
    BOOTS("---", "#-#", "#-#"),
    SWORD("-#-", "-#-", "-*-"),
    AXE("###", "#*-", "-*-");

    private final String[] shape;

    CraftingTypes(String... shape) {
        this.shape = shape;
    }

    public String[] getShape() {
        return shape;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String row : shape) {
            sb.append(row).append("\n");
        }
        return sb.toString().trim();
    }
}
