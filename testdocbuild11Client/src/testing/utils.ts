import { NgModule } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import {
  Globals,
  PickerDialogService,
  PickerComponent,
  ListFiltersComponent,
  ConfirmDialogComponent,
  ToolTipRendererDirective,
} from 'src/app/common/shared';

import {
  MatButtonModule,
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatRadioModule,
  MatTableModule,
  MatCardModule,
  MatTabsModule,
  MatInputModule,
  MatDialogModule,
  MatSelectModule,
  MatCheckboxModule,
  MatAutocompleteModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatMenuModule,
  MatTable,
  MatChipsModule,
  MatSortModule,
  MatSnackBarModule,
  MatProgressSpinnerModule,
  MatTooltipModule,
} from '@angular/material';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';
import { TranslateTestingModule } from './translate-testing.module';
import {
  ActivatedRouteMock,
  GlobalsMock,
  AuthenticationServiceMock,
  GlobalPermissionServiceMock,
  MatDialogMock,
  dialogRefMock,
} from './mocks';
import { AuthenticationService } from 'src/app/core/authentication.service';
import { GlobalPermissionService } from 'src/app/core/global-permission.service';

export var EntryComponents: any[] = [
  PickerComponent,
  ListFiltersComponent,
  ConfirmDialogComponent,
  ToolTipRendererDirective,
];

export var imports: any[] = [
  HttpClientTestingModule,
  NoopAnimationsModule,
  FormsModule,
  ReactiveFormsModule,
  MatButtonModule,
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatRadioModule,
  MatTableModule,
  MatCardModule,
  MatTabsModule,
  MatInputModule,
  MatDialogModule,
  MatSelectModule,
  MatCheckboxModule,
  MatAutocompleteModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatMenuModule,
  MatChipsModule,
  MatSortModule,
  MatSnackBarModule,
  MatProgressSpinnerModule,
  NgxMaterialTimepickerModule,
  TranslateTestingModule,
  MatTooltipModule,
];

export var exports: any[] = [
  HttpClientTestingModule,
  NoopAnimationsModule,
  FormsModule,
  ReactiveFormsModule,
  MatButtonModule,
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatListModule,
  MatRadioModule,
  MatTableModule,
  MatCardModule,
  MatTabsModule,
  MatInputModule,
  MatDialogModule,
  MatSelectModule,
  MatCheckboxModule,
  MatAutocompleteModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatMenuModule,
  MatTable,
  MatChipsModule,
  MatSortModule,
  MatSnackBarModule,
  MatProgressSpinnerModule,
  NgxMaterialTimepickerModule,
  TranslateTestingModule,
  MatTooltipModule,
];

export var providers: any[] = [
  // {provide: Router, useClass: MockRouter},
  { provide: ActivatedRoute, useValue: ActivatedRouteMock },
  { provide: Globals, useValue: GlobalsMock },
  // MatDialog,
  // {provide: PickerDialogService, useClass: MockPickerDialogService},
  { provide: AuthenticationService, useClass: AuthenticationServiceMock },
  { provide: GlobalPermissionService, useClass: GlobalPermissionServiceMock },
  { provide: MatDialog, useClass: MatDialogMock },
  { provide: MatDialogRef, useValue: dialogRefMock },
  PickerDialogService,
];

@NgModule({
  imports: imports.concat([RouterTestingModule]),
  exports: exports.concat([RouterTestingModule]),
  providers: providers,
})
export class TestingModule {
  constructor() {}
}

export function checkValues(mainObject, objToBeChecked): boolean {
  const doesValueMatch = (currentKey) => {
    return mainObject[currentKey] == objToBeChecked[currentKey];
  };
  return Object.keys(objToBeChecked).every(doesValueMatch);
}
