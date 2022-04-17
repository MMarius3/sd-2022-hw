import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthenticationService} from "../../api/services/authentication.service";


@Injectable({providedIn: "root"})
export class AuthenticationGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService,
              private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(this.authenticationService.isAuthenticated()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

}
