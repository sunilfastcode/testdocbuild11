import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClipboardModule } from 'ngx-clipboard';
import { ReportingRoutingModule } from './reporting-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { CubejsClientModule } from '@cubejs-client/ngx';

import { GenerateReportComponent } from './pages/myreports/generate-report/generate-reports.component';
import { SchemaComponent } from './pages/schema/schema.component';
import { EditDashboardComponent } from './pages/dashboard/editdashboard/editdashboard.component';
import { AddReportsToDashboardComponent } from './modalDialogs/addReportsToDashboard/addReportsToDashboard.component';
import { DropdownDirective } from './directives/dropdown.directive';
import { SchemadropdownDirective } from './directives/schemadropdown.directive';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ManageDashboardsComponent } from './pages/dashboard/manage-dashboards/manage-dashboards.component';
import { PreviewComponent } from './pages/dashboard/preview/preview.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { MyreportsComponent } from './pages/myreports/myreports.component';
import { SaveReportsComponent } from './modalDialogs/saveReports/saveReports.component';
import { AddExReportsToDashboardComponent } from './modalDialogs/addExReportsToDashboard/addExReportsToDashboard.component';

import { MatSlideToggleModule, MatMenuModule } from '@angular/material';
import { DashboardMainComponent } from './pages/dashboard/dashboard-main/dashboard-main.component';
import { MyreportsMainComponent } from './pages/myreports/myreports-main/myreports-main.component';
import { ReportDetailsComponent } from './pages/myreports/report-details/report-details.component';
import { SharedModule } from 'src/app/common/shared';
import { environment } from 'src/environments/environment';
import { UpdateDashboardComponent } from './modalDialogs/update-dashboard/update-dashboard.component';
import { ChartComponent } from './pages/chart/chart.component';
import { ChartsModule } from 'ng2-charts';

const cubejsOptions = {
  token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.K9PiJkjegbhnw4Ca5pPlkTmZihoOm42w8bja9Qs2qJg',
  options: {
    apiUrl: environment.reportingUrl + '/cubejs-api/v1',
  },
};

@NgModule({
  declarations: [
    GenerateReportComponent,
    SchemaComponent,
    DropdownDirective,
    SchemadropdownDirective,
    EditDashboardComponent,
    ManageDashboardsComponent,
    PreviewComponent,
    DashboardComponent,
    MyreportsComponent,
    AddReportsToDashboardComponent,
    AddExReportsToDashboardComponent,
    SaveReportsComponent,
    DashboardMainComponent,
    MyreportsMainComponent,
    ReportDetailsComponent,
    UpdateDashboardComponent,
    ChartComponent,
  ],
  imports: [
    ChartsModule,
    CommonModule,
    ClipboardModule,
    ReportingRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FlexLayoutModule,
    SharedModule,
    CubejsClientModule.forRoot(cubejsOptions),
    DragDropModule,
    MatSlideToggleModule,
    MatMenuModule,
  ],
  entryComponents: [
    AddReportsToDashboardComponent,
    AddExReportsToDashboardComponent,
    SaveReportsComponent,
    UpdateDashboardComponent,
  ],
})
export class ReportingModule {}
