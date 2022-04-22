import { Role } from "./role.model";

export class User {
    id?: number;
    name?: string;
    password?: string;
    email?: string;
    roles?: Role[];
}