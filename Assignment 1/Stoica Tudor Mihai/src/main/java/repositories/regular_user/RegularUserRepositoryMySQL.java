package repositories.regular_user;

import repositories.dtos.RegularUserDTO;

public class RegularUserRepositoryMySQL implements RegularUserRepository<RegularUserDTO>{
    @Override
    public void deleteByID() {

    }

    @Override
    public long create(RegularUserDTO object) {
        return 0;
    }

    @Override
    public RegularUserDTO getByID(long id) {
        return null;
    }

    @Override
    public void update(RegularUserDTO object) {

    }
}
