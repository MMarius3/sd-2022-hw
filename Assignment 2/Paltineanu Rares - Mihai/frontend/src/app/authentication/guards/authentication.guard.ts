import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthenticationService} from "../../api/services/authentication.service";


@Injectable({providedIn: "root"})
export class AuthenticationGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService,
              private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if(!this.authenticationService.isAuthenticated()) {
      this.navigateToLogin();
      return false;
    }

    let url = route.routeConfig?.path ?? '';

    if(url.indexOf('admin') > -1) {
      if(this.authenticationService.isAdmin()) {
        return true;
      }
      this.navigateToCustomer();
      return false;
    }

    return true;
  }

  private navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  private navigateToCustomer(): void {
    this.router.navigate(['/employee']);
  }
}
