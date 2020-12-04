import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { SwaggerComponent } from './swagger/swagger.component';
import { ErrorPageComponent } from './error-page/error-page.component';
/** core components and filters for authorization and authentication **/

import { AuthenticationService } from './core/authentication.service';
import { AuthGuard } from './core/auth-guard';
import { JwtInterceptor } from './core/jwt-interceptor';
import { JwtErrorInterceptor } from './core/jwt-error-interceptor';
import { GlobalPermissionService } from './core/global-permission.service';

// import { LoginComponent } from './core/login/login.component';
import { LoginExtendedComponent } from './extended/core/login/login.component';

/** end of core components and filters for authorization and authentication **/
import { ThemeService } from 'ng2-charts';
import { CubejsClientModule } from '@cubejs-client/ngx';
import { routingModule } from './app.routing';
import { SharedModule } from 'src/app/common/shared';
// import { CoreModule } from './core/core.module';
import { CoreExtendedModule } from './extended/core/core.module';
import { GeneralComponentsExtendedModule } from './common/general-components/extended/general-extended.module';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}

const cubejsOptions = {
  token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.K9PiJkjegbhnw4Ca5pPlkTmZihoOm42w8bja9Qs2qJg',
  options: {
    apiUrl: 'http://localhost:4200/cubejs-api/v1',
  },
};

@NgModule({
  declarations: [ErrorPageComponent, SwaggerComponent, AppComponent],
  imports: [
    BrowserModule,
    routingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    // CoreModule,
    CoreExtendedModule,
    SharedModule,
    GeneralComponentsExtendedModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    CubejsClientModule.forRoot(cubejsOptions),
  ],
  providers: [
    AuthenticationService,
    GlobalPermissionService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: JwtErrorInterceptor, multi: true },
    AuthGuard,
    ThemeService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [],
})
export class AppModule {}
