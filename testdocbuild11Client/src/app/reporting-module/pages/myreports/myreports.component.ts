import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatSnackBar, MatDialog, MatDialogRef } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import * as _ from 'lodash';
import { AddExReportsToDashboardComponent } from 'src/app/reporting-module/modalDialogs/addExReportsToDashboard/addExReportsToDashboard.component';
import {
  ConfirmDialogComponent,
  Globals,
  BaseListComponent,
  PickerDialogService,
  ErrorService,
  ISearchField,
  operatorType,
  listProcessingType,
} from 'src/app/common/shared';
import { ReportService } from 'src/app/reporting-module/pages/myreports/report.service';

import { IReport } from 'src/app/reporting-module/pages/myreports/ireport';
import { DashboardService } from 'src/app/reporting-module/pages/dashboard/dashboard.service';
import { Observable, ReplaySubject } from 'rxjs';

import { UserExtendedService } from 'src/app/extended/admin/user-management/user/index';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';

export enum AccessOptions {
  Login = 'Login',
  noLogin = 'noLogin',
  Password = 'Password',
}

@Component({
  selector: 'app-myreports',
  templateUrl: './myreports.component.html',
  styleUrls: ['./myreports.component.scss'],
})
export class MyreportsComponent extends BaseListComponent<IReport> implements OnInit {
  isMediumDeviceOrLess: boolean;
  showList: boolean = true;
  listFlexWidth = 30;
  detailsFlexWidth = 70;
  confirmDialogRef: MatDialogRef<ConfirmDialogComponent>;
  isLoading: boolean = true;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  manageScreenResizing() {
    this.global.isMediumDeviceOrLess$.subscribe((value) => {
      this.isMediumDeviceOrLess = value;
      if (this.isMediumDeviceOrLess) {
        this.listFlexWidth = 100;
        this.detailsFlexWidth = 100;
      } else {
        this.listFlexWidth = 30;
        this.detailsFlexWidth = 70;
      }
    });
  }

  items: IReport[] = [];
  selectedReport: IReport;
  selectedReportRunningVersion: IReport;
  selectedReportPublishedVersion: IReport;
  selectedVersion: string = 'running';
  reportUnderAction: IReport;
  allDashboardsList = [];
  searchText: string = '';

  constructor(
    public _snackBar: MatSnackBar,
    public dialog: MatDialog,
    public router: Router,
    public global: Globals,
    public reportService: ReportService,
    public dashboardService: DashboardService,
    public route: ActivatedRoute,
    public changeDetectorRefs: ChangeDetectorRef,
    public pickerDialogService: PickerDialogService,
    public errorService: ErrorService,
    public userExtendedService: UserExtendedService,
    public translate: TranslateService
  ) {
    super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, reportService, errorService);
  }

  ngOnInit() {
    this.reportService.getAllReports().subscribe((items) => {
      this.initializePageInfo();
      this.isLoading = false;
      this.items = items;
      this.updatePageInfo(items);
    });

    this.dashboardService.getAll([], 0, 1000).subscribe((res) => {
      this.allDashboardsList = res.map((v) => {
        return {
          id: v.id,
          title: v.title,
        };
      });
    });

    this.manageScreenResizing();
  }

  /**
   * Gets field based on which table is
   * currently sorted and sort direction
   * from matSort.
   * @returns String containing sort information.
   */
  getSortValue(): string {
    let sortVal = '';
    return sortVal;
  }

  applyFilters() {
    this.isLoadingResults = true;
    this.initializePageInfo();
    let sortVal = this.getSortValue();
    this.itemsObservable = this.reportService.getAllReports(
      this.searchText,
      this.currentPage * this.pageSize,
      this.pageSize,
      sortVal
    );
    this.processListObservable(this.itemsObservable, listProcessingType.Replace);
  }

  /**
   * Loads more item data when list is
   * scrolled to the bottom (virtual scrolling).
   */
  onTableScroll() {
    if (!this.isLoadingResults && this.hasMoreRecords && this.lastProcessedOffset < this.items.length) {
      this.isLoadingResults = true;
      let sortVal = this.getSortValue();
      this.itemsObservable = this.reportService.getAllReports(
        this.searchText,
        this.currentPage * this.pageSize,
        this.pageSize,
        sortVal
      );
      this.processListObservable(this.itemsObservable, listProcessingType.Append);
    }
  }

  switchVersion(event) {
    let version = event.value;
    if (version == 'published') {
      if (this.selectedReportPublishedVersion) {
        this.selectedReport = this.selectedReportPublishedVersion;
      } else {
        this.reportService.getPublishedVersion(this.selectedReport.id).subscribe((report) => {
          this.selectedReportPublishedVersion = report;
          this.selectedReport = report;
        });
      }
    } else {
      this.selectedReport = this.selectedReportRunningVersion;
    }
  }

  viewReport(report: IReport) {
    this.selectedVersion = 'running';
    this.selectedReportRunningVersion = _.clone(report);
    this.selectedReport = _.clone(report);
    this.selectedReportPublishedVersion = null;
    this.showList = false;
  }

  editReport(id) {
    this.router.navigate([`reporting/reports/${id}`]);
  }

  refreshReport(id) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'confirm',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.reportService.refresh(id).subscribe((res) => {
          this.selectedReport = res;
          this.selectedReportRunningVersion = res;
          this.selectedVersion = 'running';
          this.showMessage(this.translate.instant('REPORTING.MESSAGES.REPORT.REFRESHED'));
        });
      }
    });
  }

  publishReport(id) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'confirm',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.reportService.publish(id).subscribe((res) => {
          this.items[this.items.findIndex((x) => x.id == id)] = res;
          this.selectedReport = res;
          this.selectedReportRunningVersion = res;
          this.selectedReportPublishedVersion = null;
        });
      }
    });
  }

  deleteReport(report: IReport) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'delete',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.reportService.delete(report.id).subscribe((res) => {
          this.items = this.items.filter((v) => v.id !== report.id);
          this.showMessage(this.translate.instant('REPORTING.MESSAGES.REPORT.DELETED'));
        });
      }
    });
  }

  showMessage(msg: string): void {
    this._snackBar.open(msg, 'close', {
      duration: 2000,
    });
  }

  refreshChart() {
    this.selectedReport.query = _.clone(this.selectedReport.query);
  }

  addReporttoDashboardDialog(report: IReport): void {
    const dialogRef = this.dialog.open(AddExReportsToDashboardComponent, {
      panelClass: 'fc-modal-dialog',
      data: this.allDashboardsList,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        if (result.type === 'new') {
          const dashboardDetails = {
            userId: report.userId,
            title: result.title,
            description: result.description,
            reportDetails: [
              {
                id: report.id,
                reportWidth: result.chartSize,
              },
            ],
          };
          this.dashboardService.addExistingReportToNewDashboard(dashboardDetails).subscribe((res) => {
            this.showMessage(`${this.translate.instant('REPORTING.MESSAGES.REPORT.ADDED-TO')} ${res.title}`);
          });
        } else {
          const dashboardDetails = {
            id: result.id,
            userId: report.userId,
            reportDetails: [
              {
                id: report.id,
                reportWidth: result.chartSize,
              },
            ],
          };
          this.dashboardService.addExistingReportToExistingDashboard(dashboardDetails).subscribe((res) => {
            this.showMessage(`${this.translate.instant('REPORTING.MESSAGES.REPORT.ADDED-TO')} ${res.title}`);
          });
        }
      }
    });
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.unsubscribe();
  }
}
