import { Role } from "./role.model";
import { UserAdd } from "./user-add.model";

export class User {
    id?: number;
    name?: string;
    password?: string;
    email?: string;
    roles?: Role[];

    constructor(userAdd: UserAdd) {
        this.name = userAdd.name ?? '';
        this.password = userAdd.password ?? '';
        this.email = userAdd.email ?? '';
        this.roles = [];
    }

    getRoles(userAdd: UserAdd): Role[] {
        return [];
    }
}