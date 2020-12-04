import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';
import { ActivatedRoute, Router } from '@angular/router';
import * as _ from 'lodash';
import { MatSnackBar, MatDialogRef, MatDialog } from '@angular/material';
import { DashboardService } from '../dashboard.service';
import { IDashboard } from '../Idashboard';
import { UpdateDashboardComponent } from 'src/app/reporting-module/modalDialogs/update-dashboard/update-dashboard.component';
import { IReport } from 'src/app/reporting-module/pages/myreports/ireport';
import { ConfirmDialogComponent } from 'src/app/common/shared';
import { UserExtendedService } from 'src/app/extended/admin/user-management/user/index';
import { Observable, ReplaySubject } from 'rxjs';
import { takeUntil, take } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './editdashboard.component.html',
  styleUrls: ['./editdashboard.component.scss'],
})
export class EditDashboardComponent implements OnInit {
  confirmDialogRef: MatDialogRef<ConfirmDialogComponent>;
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);
  dashboard: IDashboard;
  selectedDashboardRunningVersion: IDashboard;
  selectedDashboardPublishedVersion: IDashboard;
  selectedVersion: string = 'running';
  dialogRef: MatDialogRef<any>;
  currentElementsPositions = {};
  constructor(
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog,
    private router: Router,
    private dashboardService: DashboardService,

    public userExtendedService: UserExtendedService,
    public translate: TranslateService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const id = params.get('id');
      this.dashboardService.getById(id).subscribe((res) => {
        this.dashboard = res;
        this.selectedDashboardRunningVersion = res;
      });
    });
  }

  switchVersion(event) {
    let version = event.value;
    if (version == 'published') {
      if (this.selectedDashboardPublishedVersion) {
        this.dashboard = this.selectedDashboardPublishedVersion;
      } else {
        this.dashboardService.getPublishedVersion(this.dashboard.id).subscribe((dashboard) => {
          this.selectedDashboardPublishedVersion = dashboard;
          this.dashboard = dashboard;
        });
      }
    } else {
      this.dashboard = this.selectedDashboardRunningVersion;
    }
  }

  deleteReport(dashboard_id, report_id) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'delete',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.dashboardService.removeReport(dashboard_id, report_id).subscribe((res) => {
          this.dashboard.reportDetails = this.dashboard.reportDetails.filter((v) => v.id !== report_id);
          this.showMessage(this.translate.instant('REPORTING.MESSAGES.DASHBOARD.REPORT-REMOVED'));
        });
      }
    });
  }

  updateDashboard() {
    var updated_reports_array = [];
    for (var i = 0; i < this.dashboard.reportDetails.length; i++) {
      updated_reports_array.push({
        id: this.dashboard.reportDetails[i].id,
        reportWidth: this.dashboard.reportDetails[i].reportWidth,
      });
    }
    const dashboard = {
      id: this.dashboard.id,
      title: this.dashboard.title,
      description: this.dashboard.description,
      userId: this.dashboard.userId,
      reportDetails: updated_reports_array,
    };
    this.dashboardService.update(this.dashboard, this.dashboard.id).subscribe((res) => {
      this.dashboard = res;
      this.selectedDashboardRunningVersion = res;
      this.selectedDashboardPublishedVersion = null;
      this.showMessage(this.translate.instant('REPORTING.MESSAGES.DASHBOARD.UPDATED'));
    });
  }

  editReport(report: IReport) {
    this.router.navigate([`reporting/reports/${report.id}`]);
  }

  editDashboard(): void {
    const dialogRef = this.dialog.open(UpdateDashboardComponent, {
      panelClass: 'fc-modal-dialog',
      data: {
        title: this.dashboard.title,
        description: this.dashboard.description,
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      this.dashboard.title = result.dashboardTitle;
      this.dashboard.description = result.dashboarddescription;
      if (result.type != 'close') {
        this.updateDashboard();
      }
    });
  }

  previewDashboard(id) {
    this.router.navigate([`reporting/dashboard/preview/${id}`]);
  }

  refreshDashboard(id) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'confirm',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.dashboardService.refresh(id).subscribe((res) => {
          this.selectedDashboardRunningVersion = res;
          this.dashboard = res;
          this.selectedVersion = 'running';
          this.showMessage(this.translate.instant('REPORTING.MESSAGES.DASHBOARD.REFRESHED'));
        });
      }
    });
  }

  publishDashboard(id) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'confirm',
      },
    });
    this.confirmDialogRef.afterClosed().subscribe((action) => {
      if (action) {
        this.dashboardService.publish(id).subscribe((res) => {
          this.dashboard = res;
          this.selectedDashboardRunningVersion = res;
          this.selectedDashboardPublishedVersion = null;
        });
      }
    });
  }

  deleteDashboard(dashboard: IDashboard) {
    this.confirmDialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: true,
      data: {
        confirmationType: 'delete',
      },
    });

    this.confirmDialogRef
      .afterClosed()
      .pipe(take(1))
      .subscribe((action) => {
        if (action) {
          this.dashboardService
            .delete(dashboard.id)
            .pipe(take(1))
            .subscribe((res) => {
              this.showMessage(this.translate.instant('REPORTING.MESSAGES.DASHBOARD.DELETED'));
              this.router.navigate(['/reporting/dashboard']);
            });
        }
      });
  }

  refreshChart(report_id) {
    this.dashboard.reportDetails[this.dashboard.reportDetails.findIndex((x) => x.id == report_id)] = _.clone(
      this.dashboard.reportDetails[this.dashboard.reportDetails.findIndex((x) => x.id == report_id)]
    );
  }

  showMessage(msg): void {
    this._snackBar.open(msg, 'close', {
      duration: 2000,
    });
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.dashboard.reportDetails, event.previousIndex, event.currentIndex);
  }

  setSize(size, index) {
    this.dashboard.reportDetails[index].reportWidth = size;
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.unsubscribe();
  }
}
