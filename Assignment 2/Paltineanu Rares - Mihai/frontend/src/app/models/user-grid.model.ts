import { User } from "./user.model";

export class UserGrid {
    id?: number;
    name?: string;
    email?: string;
    password?: string;
    roleNames?: string;

    constructor(user: User) {
        this.id = user.id ?? 0;
        this.name = user.name ?? '';
        this.email = user.email ?? '';
        this.password = user.password ?? '';
        this.roleNames = this.getRoles(user);
    }

    getRoles(user: User): string {
        let roleNames = user.roles?.map(role => role.name).join(' ') ?? ''
        return roleNames;
    }
}