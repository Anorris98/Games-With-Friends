package Server.Trophies;

public record TrophyDetailsDTO(int ID, String name, String requirementDescription, int requirement, int progress, boolean lockStatus) {
}
