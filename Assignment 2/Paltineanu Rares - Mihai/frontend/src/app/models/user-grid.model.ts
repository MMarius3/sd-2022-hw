import { User } from "./user.model";

export class UserGrid {
    id?: number;
    name?: string;
    email?: string;
    roleNames?: string;

    constructor(user: User) {
        this.id = user.id ?? 0;
        this.name = user.name ?? '';
        this.email = user.email ?? '';
    }
}