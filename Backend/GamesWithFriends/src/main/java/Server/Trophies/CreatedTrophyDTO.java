package Server.Trophies;

public record CreatedTrophyDTO(int ID, String name, String requirementDescription, int requirement, int progress, int status) {
}
