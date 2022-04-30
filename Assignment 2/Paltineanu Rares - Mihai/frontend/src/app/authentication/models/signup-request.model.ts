import { UserAdd } from "src/app/models/user-add.model";

export class SignupRequest {
  username?: string;
  email?: string;
  password?: string;
  roles?: Set<string>;
}
