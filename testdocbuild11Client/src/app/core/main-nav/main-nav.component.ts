import { Component, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
import { Router, Event, RouterEvent, NavigationEnd } from '@angular/router';
import { MatSidenav, MatSidenavContent } from '@angular/material';
import { Entities, AuthEntities, SchedulerEntities } from './entities';
import { AuthenticationService } from 'src/app/core/authentication.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';
import { UserService } from 'src/app/admin/user-management/user';

import { Globals } from 'src/app/common/shared';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss', './main-nav-mixin.component.scss'],
})
export class MainNavComponent {
  @ViewChild('drawer', { static: false }) drawer: MatSidenav;
  @ViewChild('navContent', { static: false }) navContent: MatSidenavContent;

  appName: string = 'testdocbuild11';
  selectedLanguage: string;
  entityList = Entities;

  hasTaskAppPermission: boolean = false;
  hasAdminAppPermission: boolean = false;

  isSmallDevice$: Observable<boolean>;
  isMediumDevice$: Observable<boolean>;

  themes = ['default-theme', 'alt-theme'];
  username = '';
  isResourceViewer: boolean = false;

  permissions = {};
  authEntityList = AuthEntities;
  allEntities: string[] = [...AuthEntities, ...Entities, ...SchedulerEntities, 'report'];
  modules = {
    scheduler: SchedulerEntities,
    report: ['report'],
  };

  constructor(
    public router: Router,
    public translate: TranslateService,
    public Global: Globals,

    public authenticationService: AuthenticationService,
    public globalPermissionService: GlobalPermissionService,
    public userService: UserService
  ) {
    this.isSmallDevice$ = Global.isSmallDevice$;
    this.isMediumDevice$ = Global.isMediumDevice$;

    this.selectedLanguage = localStorage.getItem('selectedLanguage');
    this.authenticationService.permissionsChange.subscribe(() => {
      this.setPermissions();
    });
    this.setPermissions();
    this.authenticationService.preferenceChange.subscribe(() => {
      this.setPreferences();
    });
    this.setPreferences();

    this.checkForResourceViewer();
  }

  checkForResourceViewer() {
    this.router.events.subscribe((event: RouterEvent) => {
      if (event instanceof NavigationEnd) {
        if (event.url.indexOf('resourceView') > -1) {
          this.isResourceViewer = true;
        } else {
          this.isResourceViewer = false;
        }
      }
    });
  }

  switchLanguage(language: string) {
    this.translate.use(language);
    localStorage.setItem('selectedLanguage', language);
    this.selectedLanguage = language;
    this.userService.updateLanguage(language).subscribe((data) => {});
  }

  setPreferences() {
    let theme = localStorage.getItem('theme');
    let language = localStorage.getItem('selectedLanguage');

    if (theme && theme != 'undefined' && theme != 'null') {
      this.changeTheme(theme, false);
    } else {
      this.changeTheme(this.themes[0], false);
    }
    if (language && language != 'undefined' && language != 'null') {
      this.selectedLanguage = language;
      this.translate.use(language);
    }
  }

  setPermissions() {
    if (this.authenticationService.decodeToken()) {
      this.username = this.authenticationService.decodeToken().sub;
    }
    this.allEntities.forEach((entity) => {
      this.permissions[entity] = this.globalPermissionService.hasPermissionOnEntity(entity, 'READ');
    });
    this.permissions['ENTITYHISTORY'] = this.globalPermissionService.hasPermission('ENTITYHISTORY');
    this.setModulesVisibility();
  }

  setModulesVisibility() {
    Object.keys(this.modules).forEach((module) => {
      let modulePermission = `show${module[0].toUpperCase() + module.slice(1)}`;
      this.permissions[modulePermission] = false;
      this.modules[module].forEach((entity) => {
        if (this.permissions[entity]) {
          this.permissions[modulePermission] = true;
          this.permissions['showTools'] = true;
        }
      });
    });
  }

  login() {
    this.router.navigate(['/login'], { queryParams: { returnUrl: 'dashboard' } });
  }

  logout() {
    this.authenticationService.logout();
    this.changeTheme(this.themes[0], false);
    this.router.navigate(['/']);
  }
  changeTheme(theme: any, updatePreference: boolean) {
    console.log('add css class');
    for (let i = 0; i < this.themes.length; i++) {
      if (document.body.className.match(this.themes[i])) {
        document.body.classList.remove(this.themes[i]);
      }
    }
    document.body.classList.add(theme);
    if (updatePreference) {
      this.userService.updateTheme(theme).subscribe((data) => {
        console.log(data);
        localStorage.setItem('theme', theme);
      });
    }
  }
}
