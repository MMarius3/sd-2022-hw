import HTTP from "@/service/HTTP";

export class AuthenticationService {

    public static async signUp() {
        HTTP.post('/sign-up')
            .then()
    }


}
