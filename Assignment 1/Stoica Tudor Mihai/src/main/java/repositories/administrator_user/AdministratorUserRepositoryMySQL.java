package repositories.administrator_user;

import repositories.dtos.AdministratorUserDTO;

public class AdministratorUserRepositoryMySQL implements AdministratorUserRepository<AdministratorUserDTO>{
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(AdministratorUserDTO object) {
        return 0;
    }

    @Override
    public AdministratorUserDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(AdministratorUserDTO object) {

    }
}
